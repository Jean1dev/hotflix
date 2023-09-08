package main

import (
	"encoder/application/services"
	"encoder/infra/database"
	"encoder/infra/queue"
	"log"
	"os"
	"strconv"

	"github.com/joho/godotenv"
	"github.com/streadway/amqp"
)

var db database.Database

func init() {
	err := godotenv.Load()
	if err != nil {
		log.Fatalf("Error loading .env files")
	}

	autoMigrateDb, _ := strconv.ParseBool(os.Getenv("AUTO_MIGRATE_DB"))
	debug, _ := strconv.ParseBool(os.Getenv("DEBUG"))

	db.AutoMigrateDb = autoMigrateDb
	db.Debug = debug
	db.DbTypeTest = os.Getenv("")
	db.Dsn = os.Getenv("")
	db.DbTypeTest = os.Getenv("")
	db.DbType = os.Getenv("")
	db.Env = os.Getenv("")
}

func main() {
	messageChannel := make(chan amqp.Delivery)
	jobReturnChannel := make(chan services.JobWorkerResult)
	dbConnection, err := db.Connect()

	if err != nil {
		log.Fatalf("error connecting db")
	}

	defer dbConnection.Close()

	rabbitMQ := queue.NewAmqp()
	ch := rabbitMQ.Connect()

	defer ch.Close()

	rabbitMQ.Consume(messageChannel)

	jobManager := services.NewManager(dbConnection, rabbitMQ, jobReturnChannel, messageChannel)
	jobManager.Start(ch)
}
