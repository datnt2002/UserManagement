package com.example.usermanagement;

public class UserModel {
    private int id;
    private String name;
    private int age;
    private String email;
    private byte[] avatar;
    public UserModel() {
    }
    public UserModel(int id, String name, int age, String email, byte[] avatar) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
        this.avatar = avatar;
    }
    public int getId() {
        return id;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public byte[] getAvatar() {
        return avatar;
    }
    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }
}
