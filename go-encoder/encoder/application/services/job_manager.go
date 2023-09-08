package services

import (
	"encoder/application/repository"
	"encoder/domain"
	"encoder/infra/queue"
	"encoding/json"
	"log"
	"os"
	"strconv"

	"github.com/jinzhu/gorm"
	"github.com/streadway/amqp"
)

type JobManager struct {
	Db               *gorm.DB
	Domain           domain.Job
	MessageChannel   chan amqp.Delivery
	JobReturnChannel chan JobWorkerResult
	RabbitMQ         *queue.RabbitMQ
}

type JobNotificationError struct {
	Message string `json:"message"`
	Error   string `json:"error"`
}

func NewManager(db *gorm.DB, rabbitMQ *queue.RabbitMQ, jobReturnChannel chan JobWorkerResult, messageChannel chan amqp.Delivery) *JobManager {
	return &JobManager{
		Db:               db,
		Domain:           domain.Job{},
		MessageChannel:   messageChannel,
		JobReturnChannel: jobReturnChannel,
		RabbitMQ:         rabbitMQ,
	}
}

func (jm *JobManager) Start(ch *amqp.Channel) {
	videoService := NewVideoService()
	videoService.VideoRepository = repository.VideoRepositoryDb{
		Db: jm.Db,
	}

	jobService := JobService{
		JobRepo:      repository.JobRepositoryDb{Db: jm.Db},
		VideoService: videoService,
	}

	concurrency, err := strconv.Atoi(os.Getenv("CONCURRENCY"))
	if err != nil {
		log.Fatalf("error loading var: CONCURRENCY")
	}

	for qtdProcesses := 0; qtdProcesses < concurrency; qtdProcesses++ {
		go JobWorker(jm.MessageChannel, jm.JobReturnChannel, jobService, jm.Domain)
	}

	for jobResult := range jm.JobReturnChannel {
		var err error
		if jobResult.Error != nil {
			err = jm.checkParserErrors(jobResult)
		} else {
			err = jm.notifySucess(jobResult, ch)
		}

		if err != nil {
			jobResult.Message.Reject(false)
		}
	}
}

func (j *JobManager) notifySucess(jobResult JobWorkerResult, ch *amqp.Channel) error {
	jobJson, err := json.Marshal(jobResult.Job)
	if err != nil {
		return err
	}

	err = j.notify(jobJson)
	if err != nil {
		return err
	}

	err = jobResult.Message.Ack(false)
	if err != nil {
		return err
	}

	return nil
}

func (j *JobManager) notify(jobJson []byte) error {
	err := j.RabbitMQ.Notify(string(jobJson), "application/json", os.Getenv("RABBITMQ_NOTIFICATION_EX"), os.Getenv("RABBITMQ_NOTIFICATION_ROUTING_KEY"))
	if err != nil {
		return err
	}

	return nil
}

func (jm *JobManager) checkParserErrors(jobResult JobWorkerResult) error {
	if jobResult.Job.ID != "" {
		log.Printf("MEssageID #{jobResult.Message.DeliveryTag}. Error with job: #{jobResult.Job.ID} with the video")
	} else {
		log.Printf("MEssageID #{jobResult.Message.DeliveryTag}. Error parsing message")
	}

	errorMessage := JobNotificationError{
		Message: string(jobResult.Message.Body),
		Error:   jobResult.Error.Error(),
	}

	jobjson, err := json.Marshal(errorMessage)

	err = jm.notify(jobjson)
	if err != nil {
		return err
	}

	err = jobResult.Message.Reject(false)
	if err != nil {
		return err
	}

	return nil
}
