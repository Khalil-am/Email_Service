package com.am.VirtueEmail.controller;

import com.am.VirtueEmail.pojo.EmailBody;
import com.am.VirtueEmail.config.MessageBody;
import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@CrossOrigin
@RestController
@RequestMapping("Mail")
public class EmailBodyController {

    @RequestMapping(value = "/EmailNotification", method = RequestMethod.POST)
    public ResponseEntity<MessageBody> updateProfile(@RequestBody EmailBody emailBody) {
        if (emailBody.getFromEmail() == null ||
                emailBody.getToEmail() == null ||
                emailBody.getContentType() == null ||
                emailBody.getSubject() == null ||
                emailBody.getBody() == null
        )
            throw new NullPointerException();

        Email from = new Email(emailBody.getFromEmail());
        String subject = emailBody.getSubject();
        Email to = new Email(emailBody.getToEmail());
        Content content = new Content(emailBody.getContentType(), emailBody.getBody());
        Mail mail = new Mail(from, subject, to, content);
        //        SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));//SENDGRID_API_KEY
        SendGrid sg = new SendGrid("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");//SENDGRID_API_KEY

        Request request1 = new Request();
        try {
            request1.setMethod(Method.POST);
            request1.setEndpoint("mail/send");
            request1.setBody(mail.build());
            //fix (The provided authorization grant is invalid)
            Response response = sg.api(request1);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            System.out.println("ex: " + ex.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}