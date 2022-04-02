package com.example.cameltesttwo.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.apache.camel.builder.DeadLetterChannelBuilder;
import org.apache.camel.component.activemq.ActiveMQComponent;
import org.apache.camel.component.jms.JmsConfiguration;
import org.apache.camel.processor.errorhandler.RedeliveryPolicy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jms.core.JmsTemplate;

import com.example.cameltesttwo.framework.SystemContext;

@Configuration
@DependsOn("systemContext")
public class CamelIntegrationConfig {

	@Bean
	public ActiveMQConnectionFactory jmsConnectionFactory2() {

		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();

		factory.setBrokerURL(SystemContext.getProperty("imb-framework.jms-broker.url"));
		factory.setUserName(SystemContext.getProperty("imb-framework.jms-broker.user"));
		factory.setPassword(SystemContext.getProperty("imb-framework.jms-broker.password"));

		return factory;
	}

	@Bean
	public PooledConnectionFactory pooledConnectionFactory(final ActiveMQConnectionFactory factory) {

		PooledConnectionFactory pool = new PooledConnectionFactory();

		pool.setMaxConnections(SystemContext.getIntProperty("imb-framework.jms-broker.max-connections"));
		pool.setConnectionFactory(factory);

		return pool;
	}

	@Bean
	public ActiveMQComponent activemq(final PooledConnectionFactory factory) {

		JmsConfiguration config = new JmsConfiguration();
		config.setConnectionFactory(factory);
		config.setConcurrentConsumers(SystemContext.getIntProperty("imb-framework.jms-broker.concurrent-consumers"));

		ActiveMQComponent activemq = new ActiveMQComponent();
		activemq.setConfiguration(config);

		return activemq;
	}

	@Bean
	public DeadLetterChannelBuilder dlq() {

		RedeliveryPolicy policy = new RedeliveryPolicy();
		policy.setMaximumRedeliveries(2);
		policy.setRedeliveryDelay(5000);

		DeadLetterChannelBuilder dlq = new DeadLetterChannelBuilder();
		dlq.setDeadLetterUri("activemq:queue:dead-letter-queue");
		dlq.setRedeliveryPolicy(policy);

		return dlq;
	}

	/**
	 *
	 * @return
	 */
	@Bean
	public JmsTemplate orderJmsTemplate() {
		JmsTemplate jmsTemplate = new JmsTemplate(jmsConnectionFactory2());

		return jmsTemplate;
	}

}
