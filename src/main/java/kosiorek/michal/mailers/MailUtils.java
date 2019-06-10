package kosiorek.michal.mailers;

import kosiorek.michal.model.Movie;
import kosiorek.michal.model.SalesStand;
import org.simplejavamail.mailer.Mailer;
import org.simplejavamail.mailer.MailerBuilder;
import org.simplejavamail.mailer.config.TransportStrategy;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static j2html.TagCreator.*;

public class MailUtils {

    // TO SEND AN EMAIL YOU NEED TO ASSIGN VALID EMAIL ADDRESS TO EMAIL2 VARIABLE AND VALID PASSWORD TO PASS VARIABLE.
    // CONFIGURED TO WORK WITH GMAIL.

    private static final String EMAIL2 = new String("");
    private static final String SMTP = new String("smtp.gmail.com");
    private static final int PORT = 465;

    private static final String PASS = new String("");

    private static MailUtils singleInstance = null;

    public Mailer mailer;

    private MailUtils() {

        mailer = MailerBuilder.withSMTPServer(SMTP, PORT, EMAIL2, PASS).withTransportStrategy(TransportStrategy.SMTP_TLS).buildMailer();

    }

    public static MailUtils getInstance() {
        if (singleInstance == null)
            singleInstance = new MailUtils();

        return singleInstance;
    }

    public static void sendEmailWithJavaxMail(String content, String subject, String toEmail) {
        Properties props = new Properties();
        props.put("mail.smtp.host", SMTP); //SMTP Host
        props.put("mail.smtp.socketFactory.port", PORT); //SSL Port
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); //SSL Factory Class
        props.put("mail.smtp.auth", "true"); //Enabling SMTP Authentication
        props.put("mail.smtp.port", PORT); //SMTP Port
        props.put("mail.smtp.ssl.enable", "true");

        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL2, PASS);
            }
        };

        Session session = Session.getDefaultInstance(props, auth);
        System.out.println("Session created");


        try {
            MimeMessage msg = new MimeMessage(session);
            //set message headers
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");
            msg.setFrom(new InternetAddress("mailermk123@gmail.com", "Mailer"));
            msg.setReplyTo(InternetAddress.parse("mailermk123@gmail.com", false));
            msg.setSubject(subject, "UTF-8");
            msg.setContent(content, "text/HTML; charset=UTF-8");

            //msg.setSentDate(LocalDate.now());

            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
            System.out.println("Message is ready");
            Transport.send(msg);

            System.out.println("EMail Sent Successfully!!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static String generateHTMLEmailFromTicketData(String customerSurname, String movieTitle, BigDecimal priceAfterDiscount, SalesStand salesStand) {

        return body(
                h2("Ticket:"),
                table(
                        tbody(
                                tr(
                                        th("Movie title:"),
                                        th("Ticket date and time:"),
                                        th("Surname:"),
                                        th("Price:")
                                ),
                                 tr(
                                        td(movieTitle),
                                        td(salesStand.getStartDateTime().toString()),
                                        td(customerSurname),
                                         td(priceAfterDiscount.toString())
                                )
                        )

                )).renderFormatted();


    }

    public static String generateHTMLEmailFromTicketList(List<SalesStand> tickets, Map<Integer, Movie> movieTitles) {

        return body(
                h2("Ordered Tickets Summary:"),
                table(
                        tbody(
                                tr(
                                        th("Movie title:"),
                                        th("Ticket date and time:")
                                ),
                                each(tickets,ticket -> tr(
                                        td(movieTitles.get(ticket.getMovieId()).getTitle()),
                                        td(ticket.getStartDateTime().toString())
                                )),
                                p("Total price of all tickets: "
                                        +tickets.stream()
                                        .map(ticket -> movieTitles.get(ticket.getMovieId()).getPrice())
                                        .reduce(BigDecimal.ZERO,BigDecimal::add)
                                )
                        )

                )).renderFormatted();


    }


}

