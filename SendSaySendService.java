package ru.usb.msbemailsendsay.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import ru.usb.email.ObjectFactory;
import ru.usb.email.Request;
import ru.usb.msbemailsendsay.entity.IntEmail;
import ru.usb.msbemailsendsay.entity.TemplateCodeEntity;
import ru.usb.msbemailsendsay.repository.IntEmailRepository;
import ru.usb.msbemailsendsay.repository.TemplateCodeRepository;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;

@Service
@EnableScheduling
@Slf4j
public class SendSaySendService {

    @Autowired
    IntEmailRepository intEmailRepository;
    @Autowired
    TemplateCodeRepository templateCodeRepository;
    @Autowired
    RestTemplate restTemplate;

    @Scheduled(cron = "${cron.send}")
    void startByCron(){
        try {
       List<IntEmail> newRecords = intEmailRepository.findIntEmailByStatusEquals("NEW");
       List<TemplateCodeEntity> templates = templateCodeRepository.findDistinctByStatusIsOrderByTemplateCodeAsc("NEW");
       templates.forEach(templateCodeEntity -> {
           String template = templateCodeEntity.getTemplateCode();
           List<IntEmail> recordsByTemplate = intEmailRepository.findIntEmailByStatusEqualsAndTemplateCode("NEW",
                   template);
           recordsByTemplate.forEach(record -> {
               issueSend(record);
           });

       });
        //POST SENDSAY
        //INSERT STATUS
    } catch (Exception e) {
            Request request = new ObjectFactory().createRequest();


        }
    }

    @PostConstruct
    @Transactional(timeout = 12000)
    String doRequest() throws IOException {
        URL url = new URL("https://api.sendsay.ru/general/api/v100/json/uralsib?apiversion=100&json=1&request.id=777"
               + "&request={\"action\":\"login\",\"login\":\"uralsib\",\"passwd\":\"hi7Diqu\"}";
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type", "application/json");
        con.setConnectTimeout(5000);
        con.setReadTimeout(5000);
        con.setInstanceFollowRedirects(false);
        int status = con.getResponseCode();
        return  String.valueOf(status);

       /* HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String fooResourceUrl = "https://api.sendsay.ru/general/api/v100/json/uralsib?apiversion=100&json=1&request.id=777";
               // "&request={\"action\":\"login\",\"login\":\"uralsib\",\"passwd\":\"hi7Diqu\"}";
        HttpEntity<String> request = new HttpEntity<>("{\"12\":\"12\"}", headers);

        String doRequest =  restTemplate.getForObject(fooResourceUrl, String.class);
        log.info("Answer: {}", doRequest);
        return  doRequest;*/
    }
    private void issueSend(IntEmail record) {

    }
}
