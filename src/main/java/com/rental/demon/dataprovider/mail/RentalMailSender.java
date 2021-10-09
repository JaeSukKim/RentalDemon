package com.rental.demon.dataprovider.mail;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
    public void sendMail(String fromAddress, String toAddress, String subject, String content) {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setFrom(fromAddress);
            helper.setTo(toAddress);
            helper.setSubject(subject);
            helper.setText(content);

            mailSender.send(message);
        } catch (MessagingException e) {
            log.error("Failed to send mail : [{}, {}, {}], error : {}",
                    toAddress, subject, content, e.getMessage());
            return;
        } catch (Exception e) {
            log.error("Failed to send mail : [{}, {}, {}], error : {}",
                    toAddress, subject, content, e.getMessage());
            return;
        }

        log.info("Success to sena mail : [{}, {}, {}]", toAddress, subject, content);

    }
}
