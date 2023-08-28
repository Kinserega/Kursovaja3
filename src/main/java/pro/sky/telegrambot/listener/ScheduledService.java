package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.NotificationTask;
import pro.sky.telegrambot.repository.NotificationTaskRepository;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;


@Service
public class ScheduledService {
    @Autowired
    private TelegramBotUpdatesListener listener;

    @Autowired
    private NotificationTaskRepository notificationTaskRepository;

    @Scheduled(cron = "0 0/1 * * * *")
    public void sendNotifications() {
        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        List<NotificationTask> notificationTaskList = notificationTaskRepository.findByDateTime(now);
        notificationTaskList.forEach((notificationTask -> {
            Long chatId = notificationTask.getChat();
            String message = notificationTask.getMessage();
            listener.sendMessage(chatId, message);
        }));
    }
}
