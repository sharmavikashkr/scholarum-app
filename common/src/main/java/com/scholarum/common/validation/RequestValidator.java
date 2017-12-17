package com.scholarum.common.validation;

public interface RequestValidator<T> {

	public void validate(T t);

}
