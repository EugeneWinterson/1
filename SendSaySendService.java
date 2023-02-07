package ru.usb.springbootcbrfmpr.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.usb.springbootcbrfmpr.Model.PostSendData;
import ru.usb.springbootcbrfmpr.Model.SendSayLoginBody;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBContext;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;

@Service
@Slf4j
public class Test {
    @Autowired
    RestTemplate restTemplate;

    @PostConstruct
    String doRequest() throws IOException, URISyntaxException {
        String request = "?apiversion=100&json=1&request.id=777&request={%22action%22:%22login%22,%22login%22:%22uralsib%22,%22passwd%22:%22hi7Diqu%22}";
        String url = "https://api.sendsay.ru/";
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT);
        SendSayLoginBody sendSayLoginBody = mapper.readValue(response.getBody(), SendSayLoginBody.class);
        if (Objects.equals(response.getStatusCode(), HttpStatus.OK)) {
            log.info("sendsay connect = true");
            issueSend(response);
        }

        return String.valueOf(response);
    }

    private void issueSend(ResponseEntity<String> response) throws JsonProcessingException {
        ObjectMapper mapper = new JsonMapper();

        mapper.writeValueAsString(createPostRequest(response));
    }

    PostSendData createPostRequest(ResponseEntity<String> response) {
        PostSendData jsonRequest = new PostSendData();

        jsonRequest.setApikey(response.toString());
        return  jsonRequest;
    }
}
