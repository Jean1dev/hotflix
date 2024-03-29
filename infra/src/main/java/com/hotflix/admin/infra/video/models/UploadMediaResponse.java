package com.hotflix.admin.infra.video.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hotflix.admin.domain.video.VideoMediaType;

public record UploadMediaResponse(
        @JsonProperty("video_id") String videoId,
        @JsonProperty("media_type") VideoMediaType mediaType
) {
}
