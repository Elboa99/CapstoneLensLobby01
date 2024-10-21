package kennyboateng.CapstoneLensLobby01.tools;


import kong.unirest.core.HttpResponse;
import kong.unirest.core.JsonNode;
import kong.unirest.core.Unirest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import kennyboateng.CapstoneLensLobby01.entities.Utente;




@Component
public class MailgunSender {
    private String apiKey;
    private String domainName;

    public MailgunSender(@Value("${mailgun.key}") String apiKey, @Value("${mailgun.domain}") String domainName) {
        this.apiKey = apiKey;
        this.domainName = domainName;
    }

    public void sendRegistrationEmail(Utente recipient) {

        HttpResponse<JsonNode> response = Unirest.post("https://api.mailgun.net/v3/" + this.domainName + "/messages")
                .basicAuth("api", this.apiKey)
                .queryString("from", "kennyboateng.99@gmail.com")
                .queryString("to", recipient.getEmail())
                .queryString("subject", "Registrazione completata")
                .queryString("text", "Buongiorno " + recipient.getNome() + " " + recipient.getCognome() + " grazie per esserti registrato")
                .asJson();
        System.out.println((response.getBody()));
    }

    public void sendMailByAdmin(String recipientEmail, String subject, String content) {
        HttpResponse<JsonNode> response = Unirest.post("https://api.mailgun.net/v3/" + this.domainName + "/messages")
                .basicAuth("api", this.apiKey)
                .queryString("from", "kennyboateng.99@gmail.com")
                .queryString("to", recipientEmail)
                .queryString("subject", subject)
                .queryString("text", content)
                .asJson();
        System.out.println(response.getBody());
    }
}