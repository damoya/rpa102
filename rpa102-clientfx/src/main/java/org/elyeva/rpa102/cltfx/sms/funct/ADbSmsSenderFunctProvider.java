package org.elyeva.rpa102.cltfx.sms.funct;

import org.elyeva.rpa102.commons.db.RpaDbConstants;

import es.indra.eplatform.EPException;
import es.indra.eplatform.data.metadata.ITable;
import es.indra.eplatform.data.record.IRecord;
import es.indra.eplatform.data.record.IRecordDataStore;
import es.indra.eplatform.data.record.RecordUtils;
import es.indra.eplatformfx.data.ui.CltDataMgmtService;

public abstract class ADbSmsSenderFunctProvider implements ISmsSenderFunctProvider {

	@Override
	public SmsSentResponse sendAndSaveSms(
			String refElement, String msg, String from, String destPhone) throws EPException {

		SmsSentResponse ret = sendSms(msg, from, destPhone);

		if (ret.getSmsState() != SmsState.ERROR) {

			final IRecordDataStore rds = CltDataMgmtService.getInstance().getRecordDataStore();

			ITable table = rds.getMetadata().getTable(RpaDbConstants.TblSms.NAME);

			IRecord record = rds.getDataFactory().create(table);

			record.setAttribute(RpaDbConstants.TblSms.COL_MSG_ID, ret.getMsgId());
			record.setAttribute(RpaDbConstants.TblSms.COL_MSG_TEXT, ret.getMsgText());
			record.setAttribute(RpaDbConstants.TblSms.COL_DEST_PHONE, destPhone);
			record.setAttribute(RpaDbConstants.TblSms.COL_FROM_TEXT, from);
			record.setAttribute(RpaDbConstants.TblSms.COL_SENT_STATE, ret.getSmsState().name());
			record.setAttribute(RpaDbConstants.TblSms.COL_REFERENCED_ELEMENT, refElement);

			rds.add(record);
		}

		return ret;
	}

	@Override
	public IRecord getSmsRecordByRefElement(String refElementId) throws EPException {

		final IRecordDataStore rds = CltDataMgmtService.getInstance().getRecordDataStore();

		return RecordUtils.getFirstRecord(
				rds.getRecordCollection(
							RpaDbConstants.TblSms.NAME,
							RpaDbConstants.TblSms.COL_REFERENCED_ELEMENT + "='" + refElementId + "'"));
	}

}
