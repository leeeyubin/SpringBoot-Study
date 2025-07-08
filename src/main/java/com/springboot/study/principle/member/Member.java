package com.springboot.study.principle.member;

public class Member {

    private Long id;
    private String name;
    private Grade graade;

    public Member(Long id, String name, Grade graade) {
        this.id = id;
        this.name = name;
        this.graade = graade;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Grade getGraade() {
        return graade;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGraade(Grade graade) {
        this.graade = graade;
    }
}
