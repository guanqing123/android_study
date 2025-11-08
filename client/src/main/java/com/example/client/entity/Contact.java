package com.example.client.entity;

public class Contact {
    public String name; // 姓名
    public String phone; // 电话
    public String email; // 邮箱

    @Override
    public String toString() {
        return "Contact{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
