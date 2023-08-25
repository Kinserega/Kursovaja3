package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import pro.sky.telegrambot.model.NotificationTask;

import java.util.Collection;
public interface NotificationTaskRepository extends JpaRepository<NotificationTask, Long> {
//    Collection<NotificationTask> findByChatId(String chatId);
}
