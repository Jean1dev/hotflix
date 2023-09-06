package services

import (
	"encoder/application/repository"
	"encoder/domain"
	"errors"
	"os"
	"strconv"
)

type JobService struct {
	Job          *domain.Job
	JobRepo      repository.JobRepository
	VideoService VideoService
}

func (j *JobService) Start() error {
	err := j.changeStatus("DOWNLOAD")
	if err != nil {
		return j.failJob(err)
	}

	err = j.VideoService.Download(os.Getenv("inputBucketName"))
	if err != nil {
		return j.failJob(err)
	}

	err = j.changeStatus("FRAGMENT")
	if err != nil {
		return j.failJob(err)
	}

	err = j.VideoService.Fragment()
	if err != nil {
		return j.failJob(err)
	}

	err = j.changeStatus("ENCODE")
	if err != nil {
		return j.failJob(err)
	}

	err = j.VideoService.Encode()
	if err != nil {
		return j.failJob(err)
	}

	err = j.performUpload()
	if err != nil {
		return j.failJob(err)
	}

	err = j.changeStatus("FINISH")
	if err != nil {
		return j.failJob(err)
	}

	err = j.VideoService.Finish()
	if err != nil {
		return j.failJob(err)
	}

	err = j.changeStatus("COMPLETED")
	if err != nil {
		return j.failJob(err)
	}

	return nil
}

func (j *JobService) performUpload() error {
	err := j.changeStatus("UPLOAD")
	if err != nil {
		return j.failJob(err)
	}

	vu := NewVideoUpload()
	vu.OutputBucket = os.Getenv("outputBucketName")
	vu.VideoPath = os.Getenv("localStoragePath") + "/" + j.VideoService.Video.ID
	concurrency, _ := strconv.Atoi(os.Getenv("CONCURRENCY"))
	doneUpload := make(chan string)

	go vu.ProcessUpload(concurrency, doneUpload)

	var uploadResult string
	uploadResult = <-doneUpload

	if uploadResult != "upload completed" {
		return j.failJob(errors.New(uploadResult))
	}

	return err
}

func (j *JobService) changeStatus(status string) error {
	var err error

	j.Job.Status = status
	j.Job, err = j.JobRepo.Update(j.Job)

	if err != nil {
		return j.failJob(err)
	}

	return nil
}

func (j *JobService) failJob(error error) error {
	j.Job.Status = "FAILED"
	j.Job.Error = error.Error()

	_, err := j.JobRepo.Update(j.Job)

	if err != nil {
		return err
	}

	return error
}
