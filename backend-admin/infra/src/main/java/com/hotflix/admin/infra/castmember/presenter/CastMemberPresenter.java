package com.hotflix.admin.infra.castmember.presenter;

import com.hotflix.admin.application.castmember.retrieve.get.CastMemberOutput;
import com.hotflix.admin.application.castmember.retrieve.list.CastMemberListOutput;
import com.hotflix.admin.infra.castmember.models.CastMemberListResponse;
import com.hotflix.admin.infra.castmember.models.CastMemberResponse;

public interface CastMemberPresenter {

    static CastMemberResponse present(final CastMemberOutput aMember) {
        return new CastMemberResponse(
                aMember.id(),
                aMember.name(),
                aMember.type().name(),
                aMember.createdAt().toString(),
                aMember.updatedAt().toString()
        );
    }

    static CastMemberListResponse present(final CastMemberListOutput aMember) {
        return new CastMemberListResponse(
                aMember.id(),
                aMember.name(),
                aMember.type().name(),
                aMember.createdAt().toString()
        );
    }
}
