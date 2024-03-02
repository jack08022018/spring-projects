package com.jpa.config;

import jakarta.jms.ConnectionFactory;
import jakarta.jms.Session;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.connection.JmsTransactionManager;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@EnableJms
@Configuration
public class ActivemqConfig {
	@Value(value = "${jms.activemq.broker-url}")
	String brokerUrl;

	@Bean
	public ActiveMQConnectionFactory connectionFactory() {
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
		factory.setBrokerURL(brokerUrl);
		factory.setTrustAllPackages(true);
		return factory;
	}

	@Bean
	public JmsTemplate jmsTemplate() {
		JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory());
		jmsTemplate.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);
		return jmsTemplate;
	}

	@Bean
	public PlatformTransactionManager jmsTransactionManager(ConnectionFactory connectionFactory) {
		return new JmsTransactionManager(connectionFactory);
	}

	@Bean(name = "jmsListenerContainerFactory")
	public JmsListenerContainerFactory jmsListenerContainerFactory(
			@Value("${jms.activemq.listener.auto-startup}") boolean autoStart,
			@Autowired PlatformTransactionManager jmsTransactionManager) {
		ActiveMQConnectionFactory connectionFactory = connectionFactory();
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		factory.setTransactionManager(jmsTransactionManager);
		factory.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);
		factory.setSessionTransacted(true);
		factory.setConnectionFactory(connectionFactory);
    	factory.setAutoStartup(autoStart);
//		core poll size=4 threads and max poll size 8 threads
		factory.setConcurrency("1-10");
		return factory;
	}

}
