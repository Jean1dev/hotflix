package com.hotflix.admin.application;

public abstract class UseCase<IN, OUT> {

    public abstract OUT execute(IN in);
}