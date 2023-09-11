package repository_test

import (
	"encoder/application/repository"
	"encoder/domain"
	"encoder/infra/database"
	"testing"
	"time"

	"github.com/stretchr/testify/require"
)

func TestVideoInsert(t *testing.T) {
	db := database.NewDbTest()
	defer db.Close()

	video := domain.NewVideo()
	video.FilePath = "path"
	video.CreatedAt = time.Now()

	repo := repository.VideoRepositoryDb{Db: db}
	repo.Insert(video)

	v, err := repo.Find(video.ID)

	require.NotEmpty(t, v.ID)
	require.Nil(t, err)
	require.Equal(t, v.ID, video.ID)
}
