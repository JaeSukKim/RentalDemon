package com.rental.demon.dataprovider.mail;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Repository;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@RequiredArgsConstructor
@Slf4j
@Repository
public class RentalMailSender implements IMailSender {

    private final JavaMailSender mailSender;

    @Override
    public void sendMail(String toAddress, String subject, String content) {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setTo(toAddress);
            helper.setSubject(subject);
            helper.setText(content);
        } catch (MessagingException e) {
            log.error("Failed to send mail : [{}, {}, {}], error : {}",
                    toAddress, subject, content, e.getMessage());
        }

        mailSender.send(message);

    }
}
