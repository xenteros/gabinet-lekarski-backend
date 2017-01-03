package pl.com.gurgul.service.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;

/**
 * Created by agurgul on 01.01.2017.
 */
@Component
public class EmailService {

    private final Logger LOG = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    JavaMailSender javaMailSender;

    public EmailStatus sendPlainText(String to, String subject, String text) {
        LOG.info("Sending email to {} with subject: {}", to, subject);
        try {
            MimeMessage mail = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, false);
            javaMailSender.send(mail);
            return new EmailStatus(to, subject, text).success();
        } catch (Exception e) {
            LOG.error("Sending email failed due to {}", e.getMessage());
            return new EmailStatus(to, subject, text).error(e.getMessage());
        }
    }
}
