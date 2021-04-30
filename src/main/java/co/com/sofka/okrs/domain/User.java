package co.com.sofka.okrs.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document
public class User {

    @Id
    private String id;

    private String name;
    private String email;
    
    private Boolean mailCompletedOkr;

    private Boolean mailCompletedKr;

    private Boolean notificationCompletedKr;

    private Boolean notificationCompletedOkr;

    public User(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.mailCompletedOkr = true;
        this.mailCompletedKr = true;
        this.notificationCompletedKr = true;
        this.notificationCompletedOkr = true;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public Boolean getMailCompletedOkr() {
        return mailCompletedOkr;
    }

    public void setMailCompletedOkr(Boolean mailCompletedOkr) {
        this.mailCompletedOkr = mailCompletedOkr;
    }

    public Boolean getMailCompletedKr() {
        return mailCompletedKr;
    }

    public void setMailCompletedKr(Boolean mailCompletedKr) {
        this.mailCompletedKr = mailCompletedKr;
    }

    public Boolean getNotificationCompletedKr() {
        return notificationCompletedKr;
    }

    public void setNotificationCompletedKr(Boolean notificationCompletedKr) {
        this.notificationCompletedKr = notificationCompletedKr;
    }

    public Boolean getNotificationCompletedOkr() {
        return notificationCompletedOkr;
    }

    public void setNotificationCompletedOkr(Boolean notificationCompletedOkr) {
        this.notificationCompletedOkr = notificationCompletedOkr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(name, user.name) && Objects.equals(email, user.email) && Objects.equals(mailCompletedOkr, user.mailCompletedOkr) && Objects.equals(mailCompletedKr, user.mailCompletedKr) && Objects.equals(notificationCompletedKr, user.notificationCompletedKr) && Objects.equals(notificationCompletedOkr, user.notificationCompletedOkr);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, mailCompletedOkr, mailCompletedKr, notificationCompletedKr, notificationCompletedOkr);
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", mailCompletedOkr=" + mailCompletedOkr +
                ", mailCompletedKr=" + mailCompletedKr +
                ", notificationCompletedKr=" + notificationCompletedKr +
                ", notificationCompletedOkr=" + notificationCompletedOkr +
                '}';
    }
}
