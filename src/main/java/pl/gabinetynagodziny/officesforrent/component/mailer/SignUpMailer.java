package pl.gabinetynagodziny.officesforrent.component.mailer;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class SignUpMailer {

    private JavaMailSender emailSender;
    private SignUpMailTextFactory signUpMailTextFactory;

    public SignUpMailer(JavaMailSender emailSender, SignUpMailTextFactory signUpMailTextFactory){
        this.emailSender = emailSender;
        this.signUpMailTextFactory = signUpMailTextFactory;
    }


    public void sendConfirmationLink(String email, String token){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(signUpMailTextFactory.getConfirmationMailSubject());
        message.setText(signUpMailTextFactory.getConfirmationMailText(token));
        emailSender.send(message);
    }
}
