package org.ysreciplace.tastely.service;

import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.ysreciplace.tastely.entity.User;

@Service
@AllArgsConstructor
@Slf4j
public class MailSendService {

    private JavaMailSender mailSender;

    public boolean sendWelcomeMessage(User user) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(user.getEmail());
        message.setSubject("[Tastely} 환영합니다.");
        message.setText("Tastely에 가입이 완료 되었습니다");
        message.setText("안녕하세요, " + user.getNickname() + "님!\nTastely에 가입하신 것을 진심으로 환영합니다.\n\n이제 다양한 레시피를 올려보세요.\n\n");
        boolean result = true;
        try {
            mailSender.send(message);
            return true;
        }catch (Exception e){
            log.error("error = {}", e);
            result = false;
        }
        return result;
    }

    public boolean sendWelcomeHtmlMessage(User user) {
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, "utf-8");
            messageHelper.setTo(user.getEmail());
            messageHelper.setSubject("[Tastely] 회원가입이 완료 되었습니다.");

            String html = "<h2>안녕하세요, Tastely입니다.</h2>";

            html += "<p>안녕하세요<h2><a href='http://192.168.10.158:8080'>Tastely</a>에 가입하신 것을 진심으로 환영합니다.</p>";
            html += "<p>이제 다양한 레시피를 올려보세요.</p>";
            html += "<p><span style='color : gray'>팀 코드노바 올림</span</</p>";
            messageHelper.setText(html, true);

        }catch (Exception e ) {

        }
        return true;
    }
}
