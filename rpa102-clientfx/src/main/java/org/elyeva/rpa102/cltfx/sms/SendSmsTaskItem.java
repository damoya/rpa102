package org.elyeva.rpa102.cltfx.sms;

import java.util.Locale;

import org.elyeva.rpa102.cltfx.Rpa102FxAppConstants;
import org.elyeva.rpa102.cltfx.sms.funct.ISmsSenderFunctProvider;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

import es.indra.eplatform.EPException;
import es.indra.eplatform.util.log.ELogger;
import es.indra.eplatformfx.app.tasks.TaskItem;
import es.indra.eplatformfx.app.tasks.TaskItemState;

public class SendSmsTaskItem extends TaskItem {

	private ISmsSenderFunctProvider smsSenderProvider;

	private String from;
	private String destPhone;
	private String msg;

	public SendSmsTaskItem(
			String taskDescription,
			ISmsSenderFunctProvider smsSenderProvider,
			String from, String destPhone, String msg) {

		descriptionProperty().set(taskDescription);
		this.from = taskDescription;
		this.msg = msg;
		this.smsSenderProvider = smsSenderProvider;

		try {
			this.destPhone = getValidDestination(destPhone);
		} catch (NumberParseException e) {
			ELogger.error(this, Rpa102FxAppConstants.LOGGER_CATEGORY,
					"Invalid Phone Number [" + destPhone + "]", e);
		} finally {
			if (this.destPhone == null) {
				currentStateProperty().set(TaskItemState.ERROR);
				currentStateDescriptionProperty().set("Invalid Phone Number");
			}
		}
	}

	@Override
	protected void run() {
		try {
			smsSenderProvider.sendSms(msg, from, destPhone);
		} catch (EPException e) {
			currentStateProperty().set(TaskItemState.ERROR);
		}
	}

	private String getValidDestination(String destination) throws NumberParseException {

		String ret = null;

		PhoneNumber phoneNumber =
				PhoneNumberUtil.getInstance().parse(destination, Locale.getDefault().getCountry());

		if (PhoneNumberUtil.getInstance().isValidNumber(phoneNumber)) {
			ret = PhoneNumberUtil.getInstance().format(phoneNumber, PhoneNumberFormat.E164);
		}

		 return ret;
	}
}
