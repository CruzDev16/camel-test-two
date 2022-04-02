package com.example.cameltesttwo.framework;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class SystemContext implements ApplicationContextAware {

	public static ApplicationContext context;
	public static Environment env;

	@Override
	public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;
		env = applicationContext.getEnvironment();
	}

	public static String getProperty(final String id) {
		return env.getProperty(id);
	}

	public static Long getLongProperty(final String id) {
		return Long.parseLong(env.getProperty(id));
	}

	public static Integer getIntProperty(final String id) {
		return Integer.parseInt(env.getProperty(id));
	}

	/**
	 * Se crea metodo para mock
	 *
	 * @param environment environment
	 */
	public static void setEnvironment(final Environment environment) {
		env = environment;
	}
}
