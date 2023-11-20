package com.hotflix.admin.infra.castmember.models;

import com.hotflix.admin.domain.castmember.CastMemberType;

public record CreateCastMemberRequest(String name, CastMemberType type) {
}
