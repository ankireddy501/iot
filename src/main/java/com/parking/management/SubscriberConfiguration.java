package com.parking.management;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SubscriberConfiguration {

	@Bean
	public MessageSubscriber sensorSubscriberMessage()
	{
        MessageSubscriber messageSubscriber = new MessageSubscriber( "pub-c-b11b8c65-22c5-4bc7-84d2-dd41949dbc58",
                                                                     "sub-c-313d4a34-2ef7-11e6-b700-0619f8945a4f");
        messageSubscriber.init();

        return messageSubscriber;
	}
}
