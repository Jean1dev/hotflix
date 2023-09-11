package services

import (
	"encoder/domain"
	"encoder/utils"
	"encoding/json"
	"os"
	"sync"
	"time"

	"github.com/google/uuid"
	"github.com/streadway/amqp"
)

type JobWorkerResult struct {
	Job     domain.Job
	Message *amqp.Delivery
	Error   error
}

var Mutex = &sync.Mutex{}

func JobWorker(messageChannel chan amqp.Delivery, returnChannel chan JobWorkerResult, jobService JobService, job domain.Job) {
	for message := range messageChannel {
		if err := utils.IsJson(string(message.Body)); err != nil {
			returnChannel <- returnJobResult(domain.Job{}, message, err)
			continue
		}

		Mutex.Lock()
		err := json.Unmarshal(message.Body, &jobService.VideoService.Video)
		jobService.VideoService.Video.ID = uuid.New().Variant().String()
		Mutex.Unlock()

		if err != nil {
			returnChannel <- returnJobResult(domain.Job{}, message, err)
			continue
		}

		err = jobService.VideoService.Video.Validate()
		if err != nil {
			returnChannel <- returnJobResult(domain.Job{}, message, err)
			continue
		}

		Mutex.Lock()
		err = jobService.VideoService.insertVideo()
		Mutex.Unlock()

		if err != nil {
			returnChannel <- returnJobResult(domain.Job{}, message, err)
			continue
		}

		job.Video = jobService.VideoService.Video
		job.OutputBucketPath = os.Getenv("outputBucketName")
		job.Status = "STARTING"
		job.CreatedAt = time.Now()

		Mutex.Lock()
		_, err = jobService.JobRepo.Insert(&job)
		Mutex.Unlock()

		if err != nil {
			returnChannel <- returnJobResult(domain.Job{}, message, err)
			continue
		}

		jobService.Job = &job
		err = jobService.Start()
		if err != nil {
			returnChannel <- returnJobResult(domain.Job{}, message, err)
			continue
		}

		returnChannel <- returnJobResult(job, message, nil)
	}
}

func returnJobResult(job domain.Job, message amqp.Delivery, err error) JobWorkerResult {
	result := JobWorkerResult{
		Job:     job,
		Message: &message,
		Error:   err,
	}

	return result
}
