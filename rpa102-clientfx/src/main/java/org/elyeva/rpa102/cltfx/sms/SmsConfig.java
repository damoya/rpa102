package org.elyeva.rpa102.cltfx.sms;

import java.util.ArrayList;
import java.util.List;

import org.elyeva.rpa102.commons.domain.IPhoneNumber;
import org.elyeva.rpa102.commons.domain.impl.PhoneNumber;

import es.indra.eplatform.util.StringUtils;

public class SmsConfig {

	private String msg;

	private List<IPhoneNumber> destinations = new ArrayList<>();

	private String from;

	private String referencedElement;

	public String getMsg() {
		return msg;
	}

	public SmsConfig msg(String msg) {
		this.msg = msg;

		return this;
	}

	public List<IPhoneNumber> getDestinations() {
		return destinations;
	}

	public SmsConfig addDestination(String phoneNumber, String description) {
		if (StringUtils.isNotEmpty(phoneNumber)) {
			addDestination(new PhoneNumber(phoneNumber, description));
		}

		return this;
	}

	public SmsConfig addDestination(IPhoneNumber phoneNumber) {
		if (phoneNumber != null) {
			destinations.add(phoneNumber);
		}

		return this;
	}

	public String getFrom() {
		return from;
	}

	public SmsConfig from(String from) {
		this.from = from;

		return this;
	}

	public String getReferencedElement() {
		return referencedElement;
	}

	public SmsConfig referencedElement(String referencedElement) {
		this.referencedElement = referencedElement;

		return this;
	}
}
