package org.elyeva.rpa102.cltfx.sms.funct.movistar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.UUID;

import javax.net.ssl.HttpsURLConnection;

import org.elyeva.rpa102.cltfx.sms.funct.ADbSmsSenderFunctProvider;
import org.elyeva.rpa102.cltfx.sms.funct.ISmsSenderFunctProvider;
import org.elyeva.rpa102.cltfx.sms.funct.SmsSentResponse;
import org.elyeva.rpa102.cltfx.sms.funct.SmsState;

import es.indra.eplatform.EPException;
import es.indra.eplatform.util.IObjectDefinition;
import es.indra.eplatform.util.StringUtils;


public class MovistarSmsSenderFunctProvider extends ADbSmsSenderFunctProvider {

	private static final String PRPTY_LOGIN_KEY = "smsMovistar.login";

	private static final String PRPTY_PASSWORD_KEY = "smsMovistar.password"; // NOSONAR


	private final String url = "https://opensms.movistar.es/aplicacionpost/loginEnvio.jsp";

	private String movistarLogin;
	private String movistarPasswd;

	public MovistarSmsSenderFunctProvider(IObjectDefinition<ISmsSenderFunctProvider> objDef) {
		movistarLogin = objDef.getProperty(MovistarSmsSenderFunctProvider.PRPTY_LOGIN_KEY);
		movistarPasswd = objDef.getProperty(MovistarSmsSenderFunctProvider.PRPTY_PASSWORD_KEY);
	}

	@Override
	public SmsSentResponse sendSms(
			String msg, String from, String destPhone) throws EPException {

		SmsSentResponse response = null;

		String httpResponse = "";

		try {
			String data = URLEncoder.encode("TM_ACTION", "UTF-8") + "=" + URLEncoder.encode("AUTHENTICATE", "UTF-8");
			data += "&" + URLEncoder.encode("TM_LOGIN", "UTF-8") + "=" + URLEncoder.encode(movistarLogin, "UTF-8");
			data += "&" + URLEncoder.encode("TM_PASSWORD", "UTF-8") + "=" + URLEncoder.encode(movistarPasswd, "UTF-8");
			data += "&" + URLEncoder.encode("to", "UTF-8") + "=" + URLEncoder.encode(destPhone, "UTF-8");
			data += "&" + URLEncoder.encode("message", "UTF-8") + "=" + URLEncoder.encode(msg, "UTF-8");

			URL url = new URL(this.url);

			HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();

			connection.addRequestProperty("Content-type", "application/x-www-form-urlencoded");
			connection.addRequestProperty("Accept-Encoding", "gzip, deflate");
			connection.addRequestProperty("Accept", "image/gif, image/x-xbitmap, image/jpeg, image/pjpeg, application/x-shockwave-flash, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
			connection.addRequestProperty("Connection", "Keep-Alive");

			connection.setDoOutput(true);
			OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
			wr.write(data);
			wr.flush();


			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				BufferedReader reader =
						new BufferedReader(new InputStreamReader(connection.getInputStream()));

				String line = reader.readLine();
				while (line != null) {
					httpResponse = line;
					line = reader.readLine();
				}
				reader.close();
			} else {
				httpResponse = connection.getResponseMessage();
			}

			connection.disconnect();

		} catch (IOException e) {
			throw new EPException(e);
		}

		if (StringUtils.isEqualIgnoreCase("OK", httpResponse)) {
			response = new SmsSentResponse(SmsState.SENT);
			response.setMsgText(msg);
			response.setMsgId(UUID.randomUUID().toString());
		}
		else {
			response = new SmsSentResponse(SmsState.ERROR);
			response.setErrorMsg(httpResponse);
			response.setMsgText(msg);
			response.setMsgId(UUID.randomUUID().toString());
		}

		return response;
	}
}
