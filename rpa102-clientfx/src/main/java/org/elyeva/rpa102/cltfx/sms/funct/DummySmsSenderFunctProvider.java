package org.elyeva.rpa102.cltfx.sms.funct;

import java.util.UUID;

import es.indra.eplatform.EPException;

/**
 * https://www.altiria.com/enviar-sms-con-java/
 */
public class DummySmsSenderFunctProvider extends ADbSmsSenderFunctProvider {

	@Override
	public SmsSentResponse sendSms(
			String msg, String from, String destPhone) throws EPException {

		try {
			Thread.currentThread().sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		SmsSentResponse ret = new SmsSentResponse(SmsState.SENT);
		ret.setMsgText(msg);
		ret.setMsgId(UUID.randomUUID().toString());

		return ret;
	}
}
