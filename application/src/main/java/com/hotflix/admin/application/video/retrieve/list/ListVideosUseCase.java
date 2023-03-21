package com.hotflix.admin.application.video.retrieve.list;

import com.hotflix.admin.application.UseCase;
import com.hotflix.admin.domain.pagination.Pagination;
import com.hotflix.admin.domain.video.VideoSearchQuery;

public abstract class ListVideosUseCase
        extends UseCase<VideoSearchQuery, Pagination<VideoListOutput>> {
}
