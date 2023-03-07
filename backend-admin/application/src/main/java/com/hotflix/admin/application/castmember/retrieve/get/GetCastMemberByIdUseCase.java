package com.hotflix.admin.application.castmember.retrieve.get;

import com.fullcycle.admin.catalogo.application.UseCase;

public sealed abstract class GetCastMemberByIdUseCase
    extends UseCase<String, CastMemberOutput>
    permits DefaultGetCastMemberByIdUseCase {
}
