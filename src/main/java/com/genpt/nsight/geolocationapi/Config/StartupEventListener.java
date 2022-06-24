package com.genpt.nsight.geolocationapi.Config;

import com.genpt.nsight.geolocationapi.Service.SiteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component //Tell to create instance of start event listener wait for any events
public class StartupEventListener {
    private static final Logger logger = LoggerFactory.getLogger(StartupEventListener.class);

    @Autowired
    SiteService sitecacheService;

    @EventListener //listen for events
    public void onApplicationReady(ApplicationReadyEvent ready){
        sitecacheService.fetchAllSites();
    }
}
