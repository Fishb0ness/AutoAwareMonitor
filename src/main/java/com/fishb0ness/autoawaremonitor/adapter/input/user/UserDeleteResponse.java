package com.fishb0ness.autoawaremonitor.adapter.input.user;

public class UserDeleteResponse {

    private long deletedUsers;

    public UserDeleteResponse(long deletedUsers) {
        this.deletedUsers = deletedUsers;
    }

    public long getDeletedUsers() {
        return deletedUsers;
    }

    public void setDeletedUsers(long deletedUsers) {
        this.deletedUsers = deletedUsers;
    }
}

