package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.NotificationTask;
import pro.sky.telegrambot.repository.NotificationTaskRepository;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
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
                sendMessage(chatId, "Hello, Sergey!!!");
            } else {
                splitMessage(chatId, messageText);
            }
        });

        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    public void splitMessage(Long chat, String message) {
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
        task.setChat(chat);
        notificationTaskRepository.save(task);
        sendMessage(chat, "Added new reminder");
    }


    public void sendMessage(Long chatId, String text) {
        SendMessage message = new SendMessage(chatId, text);
        SendResponse response = telegramBot.execute(message);
        logger.info("Response: {}", response.isOk());
        logger.info("Error code: {}", response.errorCode());
    }
}
