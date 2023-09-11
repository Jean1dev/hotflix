package repository_test

import (
	"encoder/application/repository"
	"encoder/domain"
	"encoder/infra/database"
	"testing"
	"time"

	"github.com/stretchr/testify/require"
)

func TestJObRepository(t *testing.T) {
	db := database.NewDbTest()
	defer db.Close()

	video := domain.NewVideo()
	video.FilePath = "path"
	video.CreatedAt = time.Now()

	repo := repository.VideoRepositoryDb{Db: db}
	repo.Insert(video)

	job, err := domain.NewJob("path", "pending", video)
	require.Nil(t, err)

	jobRepo := repository.JobRepositoryDb{Db: db}
	jobRepo.Insert(job)

	j, err := jobRepo.Find(job.ID)

	require.Nil(t, err)
	require.Equal(t, j.ID, job.ID)
	require.Equal(t, j.VideoID, video.ID)

}

func TestJObRepositoryUpdate(t *testing.T) {
	db := database.NewDbTest()
	defer db.Close()

	video := domain.NewVideo()
	video.FilePath = "path"
	video.CreatedAt = time.Now()

	repo := repository.VideoRepositoryDb{Db: db}
	repo.Insert(video)

	job, err := domain.NewJob("path", "pending", video)
	require.Nil(t, err)

	jobRepo := repository.JobRepositoryDb{Db: db}
	jobRepo.Insert(job)

	job.Status = "Complete"
	jobRepo.Update(job)
	j, err := jobRepo.Find(job.ID)

	require.Nil(t, err)
	require.Equal(t, j.ID, job.ID)
	require.Equal(t, j.VideoID, video.ID)
	require.Equal(t, j.Status, job.Status)
}
