package org.ibayer.personal.talktome.model;

/**
 * POJO to transport message through layers
 * @author ibrahim.bayer
 *
 */
public class Message {
	private String from;
	private String text;
	private String to;
	private String time;
	
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
}
