package org.eclipse.e4.demo.cssbridge.model;

import java.util.Date;

public class Mail {
	private Importance importance;

	private String sender;

	private String subject;

	private Date date;

	private String body;

	public void setImportance(Importance importance) {
		this.importance = importance;
	}

	public Importance getImportance() {
		return importance;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getSender() {
		return sender;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getSubject() {
		return subject;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getBody() {
		return body;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getDate() {
		return date;
	}
}
