package org.ibayer.personal.talktome.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

/**
 * Configures web socket message broker, stomp protocol and servlet container
 * 
 * @author ibrahim.bayer
 *
 */
@EnableWebSocketMessageBroker
@Configuration
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

	private static final int MESSAGE_BUFFER_SIZE = 8192;
	private static final long SECOND_IN_MILLIS = 1000L;
	private static final long HOUR_IN_MILLIS = SECOND_IN_MILLIS * 60 * 60;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.socket.config.annotation.
	 * AbstractWebSocketMessageBrokerConfigurer#configureMessageBroker(org.
	 * springframework.messaging.simp.config.MessageBrokerRegistry)
	 */
	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		// simple broker is applicable for first setup.
		// To scale application enableStompBrokerRelay has to be configured.
		// documentation :
		// https://docs.spring.io/spring/docs/current/spring-framework-reference/html/websocket.html#websocket-stomp-handle-broker-relay
		config.enableSimpleBroker("/topic");
		config.setApplicationDestinationPrefixes("/app");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.socket.config.annotation.
	 * WebSocketMessageBrokerConfigurer#registerStompEndpoints(org.
	 * springframework.web.socket.config.annotation.StompEndpointRegistry)
	 */
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/chat");
		registry.addEndpoint("/chat").withSockJS();
	}

	/**
	 * Bean for servlet container configuration. Sets message buffer size and
	 * idle timeout.
	 * 
	 * @return
	 */
	@Bean
	public ServletServerContainerFactoryBean createWebSocketContainer() {
		ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
		container.setMaxTextMessageBufferSize(MESSAGE_BUFFER_SIZE);
		container.setMaxBinaryMessageBufferSize(MESSAGE_BUFFER_SIZE);
		container.setMaxSessionIdleTimeout(HOUR_IN_MILLIS);
		container.setAsyncSendTimeout(SECOND_IN_MILLIS);
		return container;
	}

}
