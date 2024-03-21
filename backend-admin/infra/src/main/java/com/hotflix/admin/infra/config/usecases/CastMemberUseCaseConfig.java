package com.hotflix.admin.infra.config.usecases;

import com.hotflix.admin.application.castmember.create.CreateCastMemberUseCase;
import com.hotflix.admin.application.castmember.create.DefaultCreateCastMemberUseCase;
import com.hotflix.admin.application.castmember.delete.DefaultDeleteCastMemberUseCase;
import com.hotflix.admin.application.castmember.delete.DeleteCastMemberUseCase;
import com.hotflix.admin.application.castmember.retrieve.get.DefaultGetCastMemberByIdUseCase;
import com.hotflix.admin.application.castmember.retrieve.get.GetCastMemberByIdUseCase;
import com.hotflix.admin.application.castmember.retrieve.list.DefaultListCastMembersUseCase;
import com.hotflix.admin.application.castmember.retrieve.list.ListCastMembersUseCase;
import com.hotflix.admin.application.castmember.update.DefaultUpdateCastMemberUseCase;
import com.hotflix.admin.application.castmember.update.UpdateCastMemberUseCase;
import com.hotflix.admin.domain.castmember.CastMemberGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class CastMemberUseCaseConfig {

    private final CastMemberGateway castMemberGateway;

    public CastMemberUseCaseConfig(final CastMemberGateway castMemberGateway) {
        this.castMemberGateway = Objects.requireNonNull(castMemberGateway);
    }

    @Bean
    public CreateCastMemberUseCase createCastMemberUseCase() {
        return new DefaultCreateCastMemberUseCase(castMemberGateway);
    }

    @Bean
    public DeleteCastMemberUseCase deleteCastMemberUseCase() {
        return new DefaultDeleteCastMemberUseCase(castMemberGateway);
    }

    @Bean
    public GetCastMemberByIdUseCase getCastMemberByIdUseCase() {
        return new DefaultGetCastMemberByIdUseCase(castMemberGateway);
    }

    @Bean
    public ListCastMembersUseCase listCastMembersUseCase() {
        return new DefaultListCastMembersUseCase(castMemberGateway);
    }

    @Bean
    public UpdateCastMemberUseCase updateCastMemberUseCase() {
        return new DefaultUpdateCastMemberUseCase(castMemberGateway);
    }
}
