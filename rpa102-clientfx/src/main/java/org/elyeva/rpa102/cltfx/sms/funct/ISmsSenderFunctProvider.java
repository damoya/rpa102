package org.elyeva.rpa102.cltfx.sms.funct;

import es.indra.eplatform.EPException;
import es.indra.eplatform.data.record.IRecord;
import es.indra.eplatform.funct.IFunctProvider;

public interface ISmsSenderFunctProvider extends IFunctProvider {

	SmsSentResponse sendSms(String msg, String from, String destPhone) throws EPException;

	SmsSentResponse sendAndSaveSms(
			String refElement, String msg, String from, String destPhone) throws EPException;

	IRecord getSmsRecordByRefElement(String refElementId) throws EPException;
}
