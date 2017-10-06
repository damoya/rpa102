package org.elyeva.rpa102.cltfx.sms;

import java.util.Locale;

import org.elyeva.rpa102.cltfx.i18n.I18nRpa102Message;
import org.elyeva.rpa102.cltfx.sms.funct.ISmsSenderFunctProvider;
import org.elyeva.rpa102.cltfx.sms.funct.SmsSentResponse;
import org.elyeva.rpa102.cltfx.sms.funct.SmsState;
import org.elyeva.rpa102.commons.domain.IPhoneNumber;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

import es.indra.eplatform.EPException;
import es.indra.eplatform.funct.FunctService;
import es.indra.eplatform.util.StringUtils;
import es.indra.eplatform.util.i18n.I18nProvider;
import es.indra.eplatformfx.app.actions.ActionMethod;
import es.indra.eplatformfx.app.window.ADialogContent;
import es.indra.eplatformfx.app.window.DialogUtils;
import es.indra.eplatformfx.app.window.IDialog;
import es.indra.eplatformfx.commons.forms.FormPanel;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

@I18nProvider(I18nRpa102Message.class)
public class SmsDialogContent extends ADialogContent<Node> {

	private TextArea taMsg;

	private TextField tfDestination;

	private String from = "RPA";

	private Label lblMsgLength;

	private String referencedElement;

	private SmsConfig smsDefConfig;

	/**
	 * Default constructor.
	 */
	public SmsDialogContent() {

	}

	/**
	 * Default constructor.
	 */
	public SmsDialogContent(SmsConfig smsDefConfig) {
		this.smsDefConfig = smsDefConfig;
	}


	@Override
	protected Node doCreateContent(IDialog dialog) {
		FormPanel formPanel = new FormPanel(1, FormPanel.TYPE_TOP_LABEL);

		taMsg = new TextArea();
		taMsg.setWrapText(true);
		taMsg.textProperty().addListener(
				(observable, oldValue, newValue) -> {
					showLengthInfo(newValue.length());
				});

		tfDestination = new TextField();
		lblMsgLength = new Label();

		formPanel.addCmp(
				I18nRpa102Message.getString(SmsDialogContent.class, "label.destination"),
				tfDestination);
		formPanel.addCmp(
				I18nRpa102Message.getString(SmsDialogContent.class, "label.message"),
				taMsg);
		formPanel.hAlignment(HPos.RIGHT).addLbl(lblMsgLength);

		showLengthInfo(0);

		initWithDefConfig();

		return formPanel;
	}

	@Override
	protected void doDestroyContent() {
		// empty method
	}

	private void initWithDefConfig() {
		if (smsDefConfig != null) {

			if (!smsDefConfig.getDestinations().isEmpty()) {
				for (IPhoneNumber phoneNumer : smsDefConfig.getDestinations()) {
					tfDestination.setText(phoneNumer.getNumber());

					break;
				}
			}

			if (!StringUtils.isEmpty(smsDefConfig.getMsg())) {
				taMsg.setText(smsDefConfig.getMsg());
			}

			if (!StringUtils.isEmpty(smsDefConfig.getFrom())) {
				from = smsDefConfig.getFrom();
			}

			if (!StringUtils.isEmpty(smsDefConfig.getReferencedElement())) {
				referencedElement = smsDefConfig.getReferencedElement();
			}
		}
	}


	private void showLengthInfo(int textLength) {
		int numMsgs = (textLength == 0 ? 0 : (textLength / 160) + 1);

		lblMsgLength.setText(
				I18nRpa102Message.getString(
						SmsDialogContent.class, "label.msgLength",
						new Object [] { textLength, numMsgs}));
	}

	@ActionMethod(order=1, text="sendMessage")
	public void sendMessage() {

		final String destination = getValidDestination(tfDestination.getText());

		if (destination == null) {
			DialogUtils.showError(
					"Invalid Phone Number", "Invalid Phone Number1", "Invalid Phone Number2");

			return;
		}
		else {
			tfDestination.setText(destination);
		}

		final String msg = taMsg.getText();

		ISmsSenderFunctProvider provider =
				FunctService.getInstance().getImplementation(ISmsSenderFunctProvider.class);

		try {
			SmsSentResponse resp = provider.sendAndSaveSms(referencedElement, msg, from, destination);

			if (resp.getSmsState() == SmsState.ERROR) {
				DialogUtils.showError(resp.getErrorMsg());
			}
			else {
				close();
			}

		} catch (EPException e) {
			DialogUtils.showError(e);
		}
	}

	private String getValidDestination(String destination) {

		String ret = null;

		 try {
			PhoneNumber phoneNumber =
					PhoneNumberUtil.getInstance().parse(destination, Locale.getDefault().getCountry());

			if (PhoneNumberUtil.getInstance().isValidNumber(phoneNumber)) {
				ret = PhoneNumberUtil.getInstance().format(phoneNumber, PhoneNumberFormat.E164);
			}

		} catch (NumberParseException e) {
			DialogUtils.showError("Invalid Phone Number", e.getMessage(), e.getMessage(), e);
		}

		 return ret;
	}

	@ActionMethod(order=100, text="cancel")
	public void cancel() {
		close();
	}
}
