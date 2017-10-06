package org.elyeva.rpa102.cltfx.sms.funct.smspubli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Locale;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.elyeva.rpa102.cltfx.sms.funct.ADbSmsSenderFunctProvider;
import org.elyeva.rpa102.cltfx.sms.funct.ISmsSenderFunctProvider;
import org.elyeva.rpa102.cltfx.sms.funct.SmsSentResponse;
import org.elyeva.rpa102.cltfx.sms.funct.SmsState;
import org.json.JSONArray;
import org.json.JSONObject;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat;
import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberType;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

import es.indra.eplatform.EPException;
import es.indra.eplatform.util.IObjectDefinition;
import es.indra.eplatform.util.StringUtils;

/**
 *
 * https://smspubli.com/
 *
 */

public class SmsPubliSenderFunctProvider extends ADbSmsSenderFunctProvider {

	private static final String PRPTY_API_KEY = "smsPubli.apiKey";

	private static final String PRPTY_REPORT_URL = "smsPubli.reportURL";

	private static final String PRPTY_TEST_MODE = "smsPubli.testMode";

	// private String apiKey = "820ffef55f64ebede83262f76bdd44d3";

	private String apiKey = "testApi";
	private String reportUrl = null;

	private Boolean testMode = true;

	public SmsPubliSenderFunctProvider(IObjectDefinition<ISmsSenderFunctProvider> objDef) {

		apiKey = objDef.getProperty(SmsPubliSenderFunctProvider.PRPTY_API_KEY, apiKey);
		reportUrl = objDef.getProperty(SmsPubliSenderFunctProvider.PRPTY_REPORT_URL);
		testMode = objDef.getBooleanValue(SmsPubliSenderFunctProvider.PRPTY_TEST_MODE, testMode);
	}

	@Override
	public SmsSentResponse sendSms(
			String msg, String from, String destPhone) throws EPException {


		String validatedDestPhone = checkAndGetValidDestination(destPhone);

		SmsSentResponse ret;

		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost request = new HttpPost("https://api.gateway360.com/api/3.0/sms/send");
		request.addHeader("content-type", "application/json");
		request.addHeader("Accept","application/json");

		try {

			String fixedMsg = msg;

			JSONObject jsonParam = new JSONObject();

			jsonParam.put("api_key", apiKey);
			if (testMode) {
				jsonParam.put("fake", 1);
			}
			jsonParam.put("concat", 1);
			if (!StringUtils.isEmpty(reportUrl)) {
				jsonParam.put("report_url", reportUrl);
			}

			JSONObject jsonMsg = new JSONObject();
			jsonMsg.put("from", from);
			jsonMsg.put("to", validatedDestPhone);
			jsonMsg.put("text", msg);
//			jsonMsg.put("send_at", "2016-02-18 17:30:00");

			JSONArray jsonMessages = new JSONArray();
			jsonMessages.put(0, jsonMsg);

			jsonParam.put("messages", jsonMessages);

			request.setEntity(new StringEntity(jsonParam.toString(4)));

			HttpResponse response = httpClient.execute(request);

//			System.out.println(response);

			ret = getSmsSentResponse(response, fixedMsg);

		}
		catch(IOException e) {
			throw new EPException(e.getMessage(), e);
		}

		return ret;
	}


	private String checkAndGetValidDestination(String destination) throws EPException {

		String ret = null;

		 try {
			PhoneNumber phoneNumber =
					PhoneNumberUtil.getInstance().parse(destination, Locale.getDefault().getCountry());

			if (!PhoneNumberUtil.getInstance().isValidNumber(phoneNumber)) {
				throw new EPException("Invalid phone number [" + destination + "]");
			}
			else if (PhoneNumberUtil.getInstance().getNumberType(phoneNumber) != PhoneNumberType.MOBILE) {
				throw new EPException("The phone number is not a mobile phone");
			}
			else {
				ret = PhoneNumberUtil.getInstance().format(phoneNumber, PhoneNumberFormat.E164);
			}

		} catch (NumberParseException e) {
			throw new EPException("Invalid phone number [" + destination + "]", e);
		}

		 return ret;
	}

	private SmsSentResponse getSmsSentResponse(
			HttpResponse response, String fixedMsg) throws UnsupportedOperationException, IOException {

		SmsSentResponse ret;

		JSONObject jsonResponse = getJSONObjectResponse(response);

		if (jsonResponse != null && jsonResponse.has("status")) {

			if (StringUtils.isEqualIgnoreCase("ok", jsonResponse.getString("status"))) {
				JSONArray retMsgInfo = jsonResponse.getJSONArray("result");

				if (retMsgInfo != null && retMsgInfo.length() == 1) {
					JSONObject jsonMsgResponse = retMsgInfo.getJSONObject(0);

					if (StringUtils.isEqualIgnoreCase("ok", jsonMsgResponse.getString("status"))) {
						ret = new SmsSentResponse(SmsState.SENT)
									.setMsgId(jsonMsgResponse.getString("sms_id"))
									.setMsgText(fixedMsg);
					}
					else {
						ret = new SmsSentResponse(SmsState.ERROR)
								.setErrorId(jsonMsgResponse.getString("error_id"))
								.setErrorMsg(jsonMsgResponse.getString("error_msg"))
								.setMsgText(fixedMsg);
					}
				}
				else {
					ret = new SmsSentResponse(SmsState.ERROR)
							.setErrorMsg("There is no response")
							.setMsgText(fixedMsg);
				}
			}
			else {
				ret = new SmsSentResponse(SmsState.ERROR)
						.setErrorId(jsonResponse.getString("error_id"))
						.setErrorMsg(jsonResponse.getString("error_msg"))
						.setMsgText(fixedMsg);
			}
		}
		else {
			ret = new SmsSentResponse(SmsState.ERROR)
					.setErrorMsg(response.toString())
					.setMsgText(fixedMsg);
		}

		return ret;
	}


	private JSONObject getJSONObjectResponse(
			HttpResponse response) throws UnsupportedOperationException, IOException  {

		JSONObject ret = null;

		HttpEntity entity = response.getEntity();

        if (entity != null) {

            // A Simple JSON Response Read
            InputStream instream = entity.getContent();
            String responseStr = convertStreamToString(instream);
            // now you have the string representation of the HTML request
            System.out.println("RESPONSE: " + responseStr);
            instream.close();

            ret = new JSONObject(responseStr);
        }

		return ret;
	}

	private static String convertStreamToString(InputStream is) throws IOException {

	    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	    StringBuilder sb = new StringBuilder();

	    String line = null;
	    try {
	        while ((line = reader.readLine()) != null) {
	            sb.append(line + "\n");
	        }
	    } finally {
            is.close();
	    }

	    return sb.toString();
	}
}
