package com.fishb0ness.autoawaremonitor.domain.user;

public class User {

    private final UserId id;
    private UserName userName;

    public User(UserId id, UserName userName) {
        this.id = id;
        this.userName = userName;
    }

    public UserId getId() {
        return id;
    }

    public UserName getUserName() {
        return userName;
    }

    public void setUserName(UserName userName) {
        this.userName = userName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;

        if (!id.equals(user.id)) return false;
        return userName.equals(user.userName);
    }
}
