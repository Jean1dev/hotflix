package domain_test

import (
	"encoder/domain"
	"testing"
	"time"

	uuid "github.com/satori/go.uuid"
	"github.com/stretchr/testify/require"
)

func TestVideoValidadeIFVideoIsEmpty(t *testing.T) {
	video := domain.NewVideo()
	err := video.Validate()

	require.Error(t, err)
}

func TestVideoIdIsNotAUUID(t *testing.T) {
	video := domain.NewVideo()
	video.ID = "abc"
	video.ResourceID = "a"
	video.FilePath = "a"
	video.CreatedAt = time.Now()

	err := video.Validate()
	require.Error(t, err)
}

func TestCreateVideoOk(t *testing.T) {
	video := domain.NewVideo()
	video.ID = uuid.NewV4().String()
	video.ResourceID = "a"
	video.FilePath = "a"
	video.CreatedAt = time.Now()

	err := video.Validate()
	require.Nil(t, err)
}
