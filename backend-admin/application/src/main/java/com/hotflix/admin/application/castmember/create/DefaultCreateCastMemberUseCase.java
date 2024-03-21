package com.hotflix.admin.application.castmember.create;

import com.hotflix.admin.domain.castmember.CastMember;
import com.hotflix.admin.domain.castmember.CastMemberGateway;
import com.hotflix.admin.domain.exceptions.NotificationException;
import com.hotflix.admin.domain.validation.handler.NotificationHandler;

import java.util.Objects;

public non-sealed class DefaultCreateCastMemberUseCase extends CreateCastMemberUseCase {

    private final CastMemberGateway castMemberGateway;

    public DefaultCreateCastMemberUseCase(final CastMemberGateway castMemberGateway) {
        this.castMemberGateway = Objects.requireNonNull(castMemberGateway);
    }

    @Override
    public CreateCastMemberOutput execute(final CreateCastMemberCommand aCommand) {
        final var aName = aCommand.name();
        final var aType = aCommand.type();

        final var notification = NotificationHandler.create();

        final var aMember = notification.validate(() -> CastMember.newMember(aName, aType));

        if (notification.hasError()) {
            notify(notification);
        }

        return CreateCastMemberOutput.from(this.castMemberGateway.create(aMember));
    }

    private void notify(NotificationHandler notification) {
        throw new NotificationException("Could not create Aggregate CastMember", notification);
    }
}
