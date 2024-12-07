package com.fitflow.fitflow_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import java.util.Properties;

@Configuration
public class EmailConfig {

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        // Set Gmail SMTP server details
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);  // Use 465 for SSL

        // Set Gmail credentials (use environment variables for security)
        mailSender.setUsername("emaildedescartekk@gmail.com");  // Your Gmail address
        mailSender.setPassword("euht uffa eucu bwqj");    // Your Gmail app password

        // Set JavaMailSender specific properties
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }
}
