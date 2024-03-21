package com.hotflix.admin.application.castmember.update;

import com.hotflix.admin.application.UseCase;

public sealed abstract class UpdateCastMemberUseCase
        extends UseCase<UpdateCastMemberCommand, UpdateCastMemberOutput>
        permits DefaultUpdateCastMemberUseCase {
}
