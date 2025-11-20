// Class gửi email Gmail - chạy ngon với JDK 8 đến JDK 21
package Util;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailUtil {
    private static final String EMAIL = "donghtats02150@gmail.com";
    private static final String PASSWORD = "wgwr poui emvb nlja"; // App Password của bạn đang đúng

    public static void send(String to, String subject, String content) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com"); // Dòng này nữa là chắc ăn

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL, PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setContent(content, "text/html; charset=UTF-8"); // ← Sửa đúng ở đây
            Transport.send(message);
            System.out.println("Gửi mail thành công đến: " + to);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Gửi mail thất bại: " + e.getMessage());
        }
    }

    public static void sendToAllSubscribers(String title, String content) {
        // Sẽ làm sau khi có NewsletterDAO
    }
}