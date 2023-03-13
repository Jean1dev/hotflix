package com.hotflix.admin.application.castmember.delete;

import com.hotflix.admin.application.UnitUseCase;

public sealed abstract class DeleteCastMemberUseCase
    extends UnitUseCase<String>
    permits DefaultDeleteCastMemberUseCase {
}
