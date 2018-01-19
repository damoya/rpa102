package org.elyeva.rpa102.cltfx.sms.funct.altiria;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.elyeva.rpa102.cltfx.Rpa102FxAppConstants;
import org.elyeva.rpa102.cltfx.sms.funct.ADbSmsSenderFunctProvider;
import org.elyeva.rpa102.cltfx.sms.funct.ISmsSenderFunctProvider;
import org.elyeva.rpa102.cltfx.sms.funct.SmsSentResponse;
import org.elyeva.rpa102.cltfx.sms.funct.SmsState;

import es.indra.eplatform.EPException;
import es.indra.eplatform.util.IObjectDefinition;
import es.indra.eplatform.util.StringUtils;
import es.indra.eplatform.util.log.ELogger;


/**
 * https://www.altiria.com/enviar-sms-con-java/
 */
public class AltiriaSmsSenderFunctProvider extends ADbSmsSenderFunctProvider {

	private static final String PRPTY_URL_KEY = "smsAltiria.url";

	private static final String PRPTY_LOGIN_KEY = "smsAltiria.login";

	private static final String PRPTY_DOMAIN_KEY = "smsAltiria.domain";

	private static final String PRPTY_PASSWORD_KEY = "smsAltiria.password";

	private static final String PRPTY_SENDER_ID_KEY = "smsAltiria.senderId";

	private String login;
	private String passwd;
	private String senderId;
	private String domainId;
	private String url;

	public AltiriaSmsSenderFunctProvider(IObjectDefinition<ISmsSenderFunctProvider> objDef) {
		login = objDef.getProperty(AltiriaSmsSenderFunctProvider.PRPTY_LOGIN_KEY);
		passwd = objDef.getProperty(AltiriaSmsSenderFunctProvider.PRPTY_PASSWORD_KEY);
		senderId = objDef.getProperty(AltiriaSmsSenderFunctProvider.PRPTY_SENDER_ID_KEY);
		url = objDef.getProperty(
				AltiriaSmsSenderFunctProvider.PRPTY_URL_KEY,
				"http://www.altiria.net/api/http");
		domainId = objDef.getProperty(AltiriaSmsSenderFunctProvider.PRPTY_DOMAIN_KEY);
		
	}

	@Override
	public SmsSentResponse sendSms(
			String msg, String from, String destPhone) throws EPException {

		String phone = destPhone;

		if (destPhone.startsWith("+")) {
			phone = phone.substring(1);
		}

		SmsSentResponse ret = new SmsSentResponse(SmsState.SENT);
		ret.setMsgText(msg);
		ret.setMsgId(UUID.randomUUID().toString());

		//Se fija el tiempo m�ximo de espera para conectar con el servidor (5000)
		//Se fija el tiempo m�ximo de espera de la respuesta del servidor (60000)
		RequestConfig config = RequestConfig.custom()
				.setConnectTimeout(5000)
				.setSocketTimeout(60000)
				.build();

		//Se inicia el objeto HTTP
		HttpClientBuilder builder = HttpClientBuilder.create();
		builder.setDefaultRequestConfig(config);
		CloseableHttpClient httpClient = builder.build();

		//Se fija la URL sobre la que enviar la petici�n POST
		//Como ejemplo la petici�n se env�a a www.altiria.net/sustituirPOSTsms
		//Se debe reemplazar la cadena '/sustituirPOSTsms' por la parte correspondiente
		//de la URL suministrada por Altiria al dar de alta el servicio o pedir cuenta
		// de prueba
		HttpPost post = new HttpPost(url);

		//Se crea la lista de par�metros a enviar en la petici�n POST
		List<NameValuePair> parametersList = new ArrayList<NameValuePair>();
		//XX, YY y ZZ se corresponden con los valores de identificaci�n del
		//usuario en el sistema, proporcionados por Altiria al dar de alta el servicio
		//o pedir cuenta de prueba
		parametersList.add(new BasicNameValuePair("cmd", "sendsms"));
		parametersList.add(new BasicNameValuePair("domainId", domainId));
		parametersList.add(new BasicNameValuePair("login", login));
		parametersList.add(new BasicNameValuePair("passwd", passwd));
		parametersList.add(new BasicNameValuePair("dest", phone));
		parametersList.add(new BasicNameValuePair("msg", msg));
		//Remitente autorizado por Altiria al dar de alta el servicio. No disponible
		//en todos los pa�ses. Omitir el parametro si no se cuenta con ninguno.
		if (StringUtils.isNotEmpty(senderId)) {
			parametersList.add(new BasicNameValuePair("senderId", senderId));
		}

		try {
			//Se fija la codificacion de caracteres de la peticion POST
			post.setEntity(new UrlEncodedFormEntity(parametersList, "UTF-8"));
		}
		catch(UnsupportedEncodingException uex) {
			ELogger.error(this, Rpa102FxAppConstants.LOGGER_CATEGORY,
					"ERROR: codificaci�n de caracteres no soportada", uex);
		}

		CloseableHttpResponse response = null;

		try {
			//Se env�a la petici�n
			response = httpClient.execute(post);
			//Se consigue la respuesta
			String resp = EntityUtils.toString(response.getEntity());

			//Error en la respuesta del servidor
			if (response.getStatusLine().getStatusCode() != 200){
				ret.setSmsState(SmsState.ERROR);
				ret.setErrorId("" + response.getStatusLine().getStatusCode());
				ret.setErrorMsg(
						new StringBuilder()
							.append("ERROR: C�digo de error HTTP: ").append(response.getStatusLine().getStatusCode())
							.append("\nCompruebe que ha configurado correctamente la direccion suministrada por Altiria")
							.toString()
				);

				return ret;
			}
			else if (resp.startsWith("ERROR")) {
				//Se procesa la respuesta capturada en la cadena 'response'

				ret.setSmsState(SmsState.ERROR);
				ret.setErrorId(resp);
				ret.setErrorMsg("C�digo de error de Altiria. Compruebe las especificaciones [" + resp + "]");
			}
		}
		catch (IOException e) {
			throw new EPException(e);
		}
		finally {
			//En cualquier caso se cierra la conexi�n
			post.releaseConnection();
			if (response != null) {
				try {
					response.close();
				}
				catch(IOException ioe) {
					ELogger.error(this, Rpa102FxAppConstants.LOGGER_CATEGORY, ioe);
				}
			}
		}

		return ret;
	}
}
