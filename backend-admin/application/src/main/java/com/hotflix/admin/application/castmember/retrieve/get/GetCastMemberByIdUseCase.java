package com.hotflix.admin.application.castmember.retrieve.get;

import com.hotflix.admin.application.UseCase;

public sealed abstract class GetCastMemberByIdUseCase
    extends UseCase<String, CastMemberOutput>
    permits DefaultGetCastMemberByIdUseCase {
}
