package org.axonframework.samples.trader.api.users;

public class UserAuthenticatedEvent {
    private final UserId userId;

    public UserAuthenticatedEvent(UserId userId) {
        this.userId = userId;
    }

    public UserId getUserId() {
        return this.userId;
    }
}