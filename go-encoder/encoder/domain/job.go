package domain

import (
	"time"

	"github.com/asaskevich/govalidator"
	uuid "github.com/satori/go.uuid"
)

type Job struct {
	ID               string    `valid:"uuid"`
	OutputBucketPath string    `valid:"notnull"`
	Status           string    `valid:"notnull"`
	Video            *Video    `valid:"-"`
	VideoID          string    `valid:"-"`
	Error            string    `valid:"-"`
	CreatedAt        time.Time `valid:"notnull"`
	UpdatedAt        time.Time `valid:"notnull"`
}

func init() {
	govalidator.SetFieldsRequiredByDefault(true)
}

func (job *Job) prepare() {
	job.ID = uuid.NewV4().String()
	job.CreatedAt = time.Now()
	job.UpdatedAt = time.Now()
}

func NewJob(outut string, status string, video *Video) (*Job, error) {
	job := Job{
		OutputBucketPath: outut,
		Status:           status,
		Video:            video,
	}

	job.prepare()
	return &job, nil
}
