package com.hesen.dto;

import java.io.Serializable;

public class PageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Object result;

    private Object page;

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Object getPage() {
        return page;
    }

    public void setPage(Object page) {
        this.page = page;
    }
}