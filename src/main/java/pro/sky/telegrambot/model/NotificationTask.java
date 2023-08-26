package pro.sky.telegrambot.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "notification_task")
public class NotificationTask {
    @Id
//    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    @Column(name = "chat")
    private Long chat;
//    @Column(name = "message")
    private String message;
//    @Column(name = "send_date_time")
    private LocalDateTime send_date_time;

    public NotificationTask(Long id, Long chat, String message, LocalDateTime send_date_time) {
        this.id = id;
        this.chat = chat;
        this.message = message;
        this.send_date_time = send_date_time;
    }

    public NotificationTask() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChat() {
        return chat;
    }

    public void setChat(Long chat) {
        this.chat = chat;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getSend_date_time() {
        return send_date_time;
    }

    public void setSend_date_time(LocalDateTime send_date_time) {
        this.send_date_time = send_date_time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotificationTask that = (NotificationTask) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "NotificationTask{" +
                "id=" + id +
                ", chat='" + chat + '\'' +
                ", message='" + message + '\'' +
                ", send_date_time=" + send_date_time +
                '}';
    }
}
