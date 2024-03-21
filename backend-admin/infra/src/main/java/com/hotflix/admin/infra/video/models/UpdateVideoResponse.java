package com.hotflix.admin.infra.video.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UpdateVideoResponse(@JsonProperty("id") String id) {
}
