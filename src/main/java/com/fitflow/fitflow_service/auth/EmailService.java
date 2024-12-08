package com.fitflow.fitflow_service.auth;

import jakarta.mail.internet.MimeMessage;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fitflow.fitflow_service.user.model.User;
import com.fitflow.fitflow_service.user.repository.UserRepository;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private JwtService jwtService;  // Inject JwtService

    @Autowired
    private UserRepository userRepository;  // Inject UserRepository to check if email exists

    public void sendPasswordRecoveryEmail(String email) {
        // Check if the email is valid (non-empty)
        if (!StringUtils.hasText(email)) {
            throw new IllegalArgumentException("Email cannot be empty");
        }

        // Check if the email exists in the database
        Optional<User> emailExists = userRepository.findByEmail(email);

        // If email doesn't exist, throw an exception
        if (emailExists.isEmpty()) {
            throw new IllegalArgumentException("Email not found");
        }

        // Generate the email token using the JwtService
        String token = jwtService.generateEmailToken(email);

        // Create the password recovery link with the token
        String resetLink = "http://localhost:4200/user/update-password?token=" + token + "&email=" + email;

        // Create the email message
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);  // true indicates multipart (for attachments)
            helper.setTo(email);
            helper.setSubject("Recuperação de Senha - FitFlow");

            // Set the email body with HTML content
            String emailBody = "<html>" +
                    "<body>" +
                    "<p>Olá,</p>" +
                    "<p>Recebemos uma solicitação para redefinir sua senha no FitFlow. Se você fez essa solicitação, clique no link abaixo para criar uma nova senha:</p>" +
                    "<p><a href=\"" + resetLink + "\">Clique aqui para redefinir sua senha</a></p>" +
                    "<p>Este link é válido por 24 horas. Após esse período, será necessário solicitar uma nova recuperação de senha.</p>" +
                    "<p>Caso você não tenha solicitado a redefinição de senha, pode ignorar este email com segurança. Nenhuma alteração será feita em sua conta.</p>" +
                    "<p>Se precisar de ajuda, entre em contato com nossa equipe de suporte pelo email suporte@fitflow.com.</p>" +
                    "<p>Obrigado por usar o FitFlow para alcançar seus objetivos!</p>" +
                    "<p>Atenciosamente,</p>" +
                    "<p>Equipe FitFlow</p>" +
                    "</body>" +
                    "</html>";

            helper.setText(emailBody, true); // true means the content is HTML
            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send recovery email", e);
        }
    }
}
