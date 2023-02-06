package ru.usb.msbemailsendsay.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@EnableScheduling
@Slf4j
@Service
public class SendSayStatusService {

    @Scheduled(cron = "${cron.status}")
    void startByCron() {
        //JSON REQUEST
        //SELECT DATA BY TEMPLATE
        //POST SENDSAY
        //INSERT STATUS
    }
}
