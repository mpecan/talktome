package org.ibayer.personal.talktome.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.ibayer.personal.talktome.model.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

/**
 * Controller class for managing message push through web socket.
 * 
 * @author ibrahim.bayer
 *
 */
@Controller
public class MessageController {

	private final SimpMessagingTemplate simpMessagingTemplate;

	/**
	 * Constructor for object
	 * 
	 * @param simpMessagingTemplate
	 */
	public MessageController(final SimpMessagingTemplate simpMessagingTemplate) {
		this.simpMessagingTemplate = simpMessagingTemplate;
	}

	/**
	 * Responsible for sharing message through web socket.s
	 * 
	 * @param message
	 *            to share with audience.
	 * @return
	 */
	@MessageMapping("/message")
	@SendTo("/topic/message")
	public Message send(Message message) {
		String time = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
		message.setTime(time);
		simpMessagingTemplate.convertAndSendToUser(message.getTo(), "/topic/message", message);
		// TODO this line isn't working i couldn't find the reason. Message is
		// broadcasted to all.
		return message;
	}
}
