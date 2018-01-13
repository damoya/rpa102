package org.elyeva.rpa102.cltfx.sms.funct;

public class SmsSentResponse {
	private String msgId;
	private String text;
	private String errorId;
	private String errorMsg;
	private SmsState smsState;

	public SmsSentResponse(SmsState state) {
		setSmsState(state);
	}

	public String getMsgId() {
		return msgId;
	}

	public String getMsgText() {
		return text;
	}

	public SmsState getSmsState() {
		return smsState;
	}

	public String getErrorId() {
		return errorId;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public SmsSentResponse setMsgId(String id) {
		this.msgId = id;

		return this;
	}

	public SmsSentResponse setMsgText(String msg) {
		this.text = msg;

		return this;
	}

	public SmsSentResponse setSmsState(SmsState state) {
		this.smsState = state;

		return this;
	}

	public SmsSentResponse setErrorId(String id) {
		this.errorId = id;

		return this;
	}

	public SmsSentResponse setErrorMsg(String msg) {
		this.errorMsg = msg;

		return this;
	}
}
