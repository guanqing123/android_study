package com.example.client.entity;

public class ImageInfo {
    public long id; // 图片编号
    public String title; // 图片标题
    public long size; // 图片大小
    public String path; // 图片路径

    @Override
    public String toString() {
        return "ImageInfo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", size=" + size +
                ", path='" + path + '\'' +
                '}';
    }
}
