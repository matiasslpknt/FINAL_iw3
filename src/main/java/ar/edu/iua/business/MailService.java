package ar.edu.iua.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    private JavaMailSender javaMailSender;

    @Autowired
    public MailService(JavaMailSender javaMailSender){
        this.javaMailSender = javaMailSender;
    }

    public void enviarCorreo(String email){
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(email);
        mail.setFrom("matiasslpknt08@gmail.com");
        mail.setSubject("Mail de prueba");
        mail.setText("Esto es una prueba");

        javaMailSender.send(mail);
    }
}
