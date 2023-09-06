package queue

import (
	"log"
	"os"

	"github.com/streadway/amqp"
)

type RabbitMQ struct {
	User              string
	Password          string
	Host              string
	Port              string
	Vhost             string
	ConsumerQueueName string
	ConsumerName      string
	AutoAck           bool
	Args              amqp.Table
	Channel           *amqp.Channel
}

func NewAmqp() *RabbitMQ {
	amqpArgs := amqp.Table{}
	amqpArgs["x-dead-letter-exchange"] = os.Getenv("AMQP_DLX")

	rabbitMQ := RabbitMQ{
		User:              os.Getenv("AMQP_DEFAULT_USER"),
		Password:          os.Getenv("AMQP_PASS"),
		Host:              os.Getenv("AMQP_HOST"),
		Port:              os.Getenv("AMQP_PORT"),
		Vhost:             os.Getenv("AMQP_VHOST"),
		ConsumerQueueName: os.Getenv("AMQP_CONSUMER_QUEUE"),
		ConsumerName:      os.Getenv("AMQP_CONSUMER_NAME"),
		AutoAck:           false,
		Args:              amqpArgs,
	}

	return &rabbitMQ
}

func (r *RabbitMQ) Connect() *amqp.Channel {
	dsn := "amqp://" + r.User + ":" + r.Password + "@" + r.Host + ":" + r.Port + r.Vhost
	conn, err := amqp.Dial(dsn)
	failOnError(err, "Failed to connect amqp")

	r.Channel, err = conn.Channel()
	failOnError(err, "Failed to open channel")

	return r.Channel
}

func (r *RabbitMQ) Consume(messageChannel chan amqp.Delivery) {

	q, err := r.Channel.QueueDeclare(
		r.ConsumerQueueName, // name
		true,                // durable
		false,               // delete when usused
		false,               // exclusive
		false,               // no-wait
		r.Args,              // arguments
	)
	failOnError(err, "failed to declare a queue")

	incomingMessage, err := r.Channel.Consume(
		q.Name,         // queue
		r.ConsumerName, // consumer
		r.AutoAck,      // auto-ack
		false,          // exclusive
		false,          // no-local
		false,          // no-wait
		nil,            // args
	)
	failOnError(err, "Failed to register a consumer")

	go func() {
		for message := range incomingMessage {
			log.Println("Incoming new message")
			messageChannel <- message
		}
		log.Println("RabbitMQ channel closed")
		close(messageChannel)
	}()
}

func (r *RabbitMQ) Notify(message string, contentType string, exchange string, routingKey string) error {

	err := r.Channel.Publish(
		exchange,   // exchange
		routingKey, // routing key
		false,      // mandatory
		false,      // immediate
		amqp.Publishing{
			ContentType: contentType,
			Body:        []byte(message),
		})

	if err != nil {
		return err
	}

	return nil
}

func failOnError(err error, msg string) {
	if err != nil {
		log.Fatalf("%s: %s", msg, err)
	}
}
