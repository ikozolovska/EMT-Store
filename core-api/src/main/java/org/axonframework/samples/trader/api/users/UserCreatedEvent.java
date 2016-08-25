package org.axonframework.samples.trader.api.users;

public class UserCreatedEvent {

    private UserId userId;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String password;

    public UserCreatedEvent(UserId userId, String firstName, String lastName, String username, String email, String phone, String password) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName=lastName;
        this.username = username;
        this.email=email;
        this.phone=phone;
        this.password = password;
    }

    public UserId getUserIdentifier() {
        return this.userId;
    }

    public String getFirstName() { return firstName; }

    public String getLastName() { return lastName; }

    public String getEmail() { return email; }

    public String getPhone() { return phone; }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "UserCreatedEvent{" +
                "userId=" + userId +
                ", first name='" + firstName + '\'' +
                ", last name='" + lastName + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}