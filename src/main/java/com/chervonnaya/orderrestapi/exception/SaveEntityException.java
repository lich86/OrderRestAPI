package com.chervonnaya.orderrestapi.exception;

import lombok.Getter;

@Getter
public class SaveEntityException extends RuntimeException {
    private String entityClass;
    private Long id;

    public SaveEntityException(String simpleName) {
        this.entityClass = simpleName;
    }

    public SaveEntityException(String entityClass, Long id) {
        this.entityClass = entityClass;
        this.id = id;
    }
}
