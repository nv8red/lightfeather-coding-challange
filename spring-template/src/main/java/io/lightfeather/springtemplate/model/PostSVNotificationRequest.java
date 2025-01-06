package io.lightfeather.springtemplate.model;

public class PostSVNotificationRequest {
    private final String firstName;
    private final String lastName;
    private final String supervisor;
    private final String phone;
    private final String email;

    public PostSVNotificationRequest(String firstName, String lastName, String supervisor, String phone, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.supervisor = supervisor;
        this.phone = phone;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getSupervisor() {
        return supervisor;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    /** Hope it's cool I used the default toString here. */
    @Override
    public String toString() {
        return "PostSVNotificationRequest{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", supervisor='" + supervisor + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
