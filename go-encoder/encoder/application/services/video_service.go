package services

import (
	"context"
	"encoder/application/repository"
	"encoder/domain"
	"io/ioutil"
	"log"
	"os"
	"os/exec"

	"cloud.google.com/go/storage"
)

type VideoService struct {
	Video           *domain.Video
	VideoRepository repository.VideoRepo
}

func NewVideoService() VideoService {
	return VideoService{}
}

func (v *VideoService) Download(bucketName string) error {
	ctx := context.Background()
	client, err := storage.NewClient(ctx)

	if err != nil {
		return err
	}

	bucket := client.Bucket(bucketName)
	obj := bucket.Object(v.Video.FilePath)
	r, err := obj.NewReader(ctx)

	if err != nil {
		return err
	}

	defer r.Close()

	body, err := ioutil.ReadAll(r)
	if err != nil {
		return err
	}

	pathname := os.Getenv("localStoragePath") + "/" + v.Video.ID + ".mp4"
	f, err := os.Create(pathname)
	if err != nil {
		return err
	}

	_, err = f.Write(body)
	if err != nil {
		return err
	}

	defer f.Close()
	log.Printf("video %v has ben stored", v.Video.ID)
	return nil
}

func (v *VideoService) Fragment() error {
	pathname := os.Getenv("localStoragePath") + "/" + v.Video.ID
	err := os.Mkdir(pathname, os.ModePerm)
	if err != nil {
		return err
	}

	source := pathname + ".mp4"
	target := pathname + ".frag"

	cmd := exec.Command("mp4fragment", source, target)
	output, err := cmd.CombinedOutput()
	if err != nil {
		return err
	}

	prinOutput(output)

	return nil
}

func (v *VideoService) Encode() error {
	cmdArgs := []string{}
	cmdArgs = append(cmdArgs, os.Getenv("localSotragePath")+"/"+v.Video.ID+".frag")
	cmdArgs = append(cmdArgs, "--use-segment-timeline")
	cmdArgs = append(cmdArgs, "-o")
	cmdArgs = append(cmdArgs, os.Getenv("localSotragePath")+"/"+v.Video.ID)
	cmdArgs = append(cmdArgs, "-f")
	cmdArgs = append(cmdArgs, "--exec-dir")
	cmdArgs = append(cmdArgs, "/opt/bento4/bin/")
	cmd := exec.Command("mp4dash", cmdArgs...)

	output, err := cmd.CombinedOutput()
	if err != nil {
		return err
	}

	prinOutput(output)

	return nil
}

func (v *VideoService) Finish() error {
	err := os.Remove(os.Getenv("localStoragePath") + "/" + v.Video.ID + ".mp4")
	if err != nil {
		log.Println("error removing mp4", v.Video.ID, ".mp4")
		return err
	}

	err = os.Remove(os.Getenv("localStoragePath") + "/" + v.Video.ID + ".frag")
	if err != nil {
		log.Println("error removing mp4", v.Video.ID, ".frag")
		return err
	}

	err = os.RemoveAll(os.Getenv("localStoragePath") + "/" + v.Video.ID)
	if err != nil {
		log.Println("error removing folder")
		return err
	}

	return nil
}

func prinOutput(out []byte) {
	if len(out) > 0 {
		log.Printf("===> Output %s\n", string(out))
	}
}
