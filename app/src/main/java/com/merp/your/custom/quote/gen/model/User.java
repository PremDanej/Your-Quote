package com.merp.your.custom.quote.gen.model;

public class User {

    private Integer id;
    private String name;
    private String username;
    private String email;
    private byte[] image;
    private Integer isVerified; // 1 -> isVerified, 0 -> Not Verified

    public User() {
    }

    public User(Integer id, String name, String username, String email, byte[] image, Integer isVerified) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.image = image;
        this.isVerified = isVerified;
    }

    public User(String name, String username, String email, byte[] image, Integer isVerified) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.image = image;
        this.isVerified = isVerified;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Integer getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(Integer isVerified) {
        this.isVerified = isVerified;
    }
}
