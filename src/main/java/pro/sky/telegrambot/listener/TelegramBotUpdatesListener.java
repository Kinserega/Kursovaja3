package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.NotificationTask;
import pro.sky.telegrambot.repository.NotificationTaskRepository;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    @Autowired
    private TelegramBot telegramBot;
    @Autowired
    private NotificationTaskRepository notificationTaskRepository;

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            String messageText = update.message().text();
            Long chatId = update.message().chat().id();
            if (update.message().text() != null && messageText.equals("/start")) {
                SendMessage message = new SendMessage(chatId, "Hello, Sergey!");
                telegramBot.execute(message);
            }
            splitMessage(chatId, messageText);
        });

        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    private void splitMessage(Long chatId, String message) {
        Pattern pattern = Pattern.compile("([0-9\\.\\:\\s]{16})(\\s)([\\W+]+)");
        Matcher matcher = pattern.matcher(message);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid message format");
        }

        String dateTimeString = matcher.group(1);
        String reminderText = matcher.group(3);

        LocalDateTime reminderDate = LocalDateTime.parse(dateTimeString, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));


        NotificationTask task = new NotificationTask();
        task.setSend_date_time(reminderDate);
        task.setMessage(reminderText);
        task.setChat(chatId);
        notificationTaskRepository.save(task);
    }
}
