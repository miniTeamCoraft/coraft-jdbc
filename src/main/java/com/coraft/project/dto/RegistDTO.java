package com.coraft.project.dto;

public class RegistDTO {
    private String id;
    private int lecCode;

    public RegistDTO() {}

    public RegistDTO(String id, int lecCode) {
        this.id = id;
        this.lecCode = lecCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getLecCode() {
        return lecCode;
    }

    public void setLecCode(int lecCode) {
        this.lecCode = lecCode;
    }

    @Override
    public String toString() {
        return "RegistDTO{" +
                "id='" + id + '\'' +
                ", lecCode=" + lecCode +
                '}';
    }
}
