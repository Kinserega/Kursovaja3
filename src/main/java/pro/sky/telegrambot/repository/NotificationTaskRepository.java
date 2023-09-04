package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.telegrambot.model.NotificationTask;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
@Repository
public interface NotificationTaskRepository extends JpaRepository<NotificationTask, Long> {

    @Query(value = "SELECT * FROM notification_task n WHERE n.send_date_time = ?1", nativeQuery = true)
    List<NotificationTask> findByDateTime(LocalDateTime date_and_time);
}
