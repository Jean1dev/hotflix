package domain

import (
	"time"

	"github.com/asaskevich/govalidator"
	uuid "github.com/satori/go.uuid"
)

type Job struct {
	ID               string    `json:"job_id" valid:"uuid" gorm:"type:uuid;primary_key"`
	OutputBucketPath string    `json:"output_bucket_path" valid:"notnull"`
	Status           string    `json:"status" valid:"notnull"`
	Video            *Video    `json:"video" valid:"-"`
	VideoID          string    `json:"-" valid:"-" gorm:"column:video_id;type:uuid;notnull"`
	Error            string    `valid:"-"`
	CreatedAt        time.Time `json:"created_at" valid:"notnull"`
	UpdatedAt        time.Time `json:"updated_at" valid:"notnull"`
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
