package com.iktpreobuka.schoolEDiary.models;

public class EmailObject {

	private String mailReceiver;

	private String subject;

	private String text;

	public EmailObject() {
		super();
	}

	@Override
	public String toString() {
		return "EmailObject [mailReceiver=" + mailReceiver + ", subject=" + subject + ", text=" + text + "]";
	}

	public String getMailReceiver() {
		return mailReceiver;
	}

	public void setMailReceiver(String mailReceiver) {
		this.mailReceiver = mailReceiver;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
