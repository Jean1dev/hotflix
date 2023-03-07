package com.hotflix.admin.application.castmember.delete;

import com.fullcycle.admin.catalogo.application.UnitUseCase;

public sealed abstract class DeleteCastMemberUseCase
    extends UnitUseCase<String>
    permits DefaultDeleteCastMemberUseCase {
}
