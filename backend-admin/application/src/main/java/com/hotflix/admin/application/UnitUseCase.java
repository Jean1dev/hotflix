package com.hotflix.admin.application;

public abstract class UnitUseCase<IN> {

    public abstract void execute(IN in);
}