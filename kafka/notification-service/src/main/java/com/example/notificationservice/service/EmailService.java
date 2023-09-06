package com.example.notificationservice.service;

import com.example.notificationservice.dto.MessageDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;

public interface EmailService{
    void sendEmail(MessageDto messageDto);
}

@Service
class EmailServiceImpl implements EmailService{
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String from;

    @Override
    @Async
    public void sendEmail(MessageDto messageDto) {
        try {
            logger.info("START... Sending email");

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, StandardCharsets.UTF_8.name());

            // load template email with content
            Context context = new Context();
            context.setVariable("name", messageDto.getToName());
            context.setVariable("content", messageDto.getContent());
            String html = templateEngine.process("welcome-email", context);

            // send email
            helper.setTo(messageDto.getTo());
            helper.setText(html, true);
            helper.setSubject(messageDto.getSubject());
            helper.setFrom(from);
//            javaMailSender.send(message);

            logger.info(messageDto.getToName());
            logger.info("END...Email sent success");
        }catch (MessagingException e) {
            logger.error("Email sent with error: " + e.getMessage());
        }catch ( MailException e) {
            logger.error("Email sent with error: " + e.getMessage());
        }
    }
}
