package com.momchil.TU4ALL.utils;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.DataHandler;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;
import java.io.*;
import java.util.Properties;

public class MailMessage extends Thread {

    static Logger logger = LoggerFactory.getLogger(MailMessage.class);


    public synchronized static boolean sendSingle(String t_from, String t_to, String t_subject, String t_simpleMessage, String smtpServer) throws MessagingException {


        Properties mailSmtpHost = new Properties();
        mailSmtpHost.setProperty("mail.smtp.host", smtpServer);
        Session mailSession = Session.getDefaultInstance(mailSmtpHost, null);

        com.momchil.TU4ALL.utils.Logger.log(com.momchil.TU4ALL.utils.Logger._DEBUG_LEVEL, "mail.smtp.host : " + mailSmtpHost.getProperty("mail.smtp.host"));

        InternetAddress from = new InternetAddress(t_from);
        InternetAddress to = new InternetAddress(t_to);
        Message msg = new MimeMessage(mailSession);
        msg.setFrom(from);
        msg.setRecipient(Message.RecipientType.TO, to);


        try {
            t_subject = MimeUtility.encodeText(t_subject, "utf-8", null);
        } catch (UnsupportedEncodingException e1) {
            logger.debug(ExceptionUtils.getStackTrace(e1));
        }

        msg.setSubject(t_subject);

        MimeMultipart msgMultiPart = new MimeMultipart();
        DataHandler dh1 = new DataHandler(t_simpleMessage, "text/html; charset=utf-8");
        MimeBodyPart bp1 = new MimeBodyPart();
        bp1.setDataHandler(dh1);
        bp1.setHeader("Content-Transfer-Encoding", "quoted-printable");
        bp1.setHeader("Content-Type", "text/html; charset=\"utf-8\"");
        msgMultiPart.addBodyPart(bp1);

        msg.setContent(msgMultiPart);

        msg.saveChanges();
        logger.debug("MailMessage [sendSingle] -> Sending email to... " + t_to);

        Transport.send(msg);
        return true;
    }


    public static boolean sendMultiple(String t_from, String t_to, String t_subject, String t_simpleMessage, String smtpServer) throws MessagingException {

        Properties mailSmtpHost = new Properties();
        mailSmtpHost.setProperty("mail.smtp.host", smtpServer);
        Session mailSession = Session.getDefaultInstance(mailSmtpHost, null);
        com.momchil.TU4ALL.utils.Logger.log(com.momchil.TU4ALL.utils.Logger._DEBUG_LEVEL, "mail.smtp.host : " + mailSmtpHost.getProperty("mail.smtp.host"));

        InternetAddress from = new InternetAddress(t_from);
        InternetAddress[] to = InternetAddress.parse(t_to);
        Message msg = new MimeMessage(mailSession);
        msg.setFrom(from);
        msg.setRecipients(Message.RecipientType.BCC, to);
        msg.setSubject(t_subject);

        MimeMultipart msgMultiPart = new MimeMultipart();
        DataHandler dh1 = new DataHandler(t_simpleMessage, "text/html; charset=utf-8");
        MimeBodyPart bp1 = new MimeBodyPart();
        bp1.setDataHandler(dh1);
        bp1.setHeader("Content-Transfer-Encoding", "quoted-printable");
        bp1.setHeader("Content-Type", "text/html; charset=\"utf-8\"");
        msgMultiPart.addBodyPart(bp1);

        msg.setContent(msgMultiPart);

        msg.saveChanges();

        Transport.send(msg);
        return true;
    }

    private Message msg;

    public MailMessage(Message msg) {
        this.msg = msg;
    }

    public void run() {
        try {
            Transport.send(msg);
        } catch (Exception e) {
            logger.debug(ExceptionUtils.getStackTrace(e));
        }
    }
}
