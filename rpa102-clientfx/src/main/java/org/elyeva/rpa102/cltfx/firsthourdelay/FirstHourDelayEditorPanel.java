package org.elyeva.rpa102.cltfx.firsthourdelay;

import org.elyeva.rpa102.cltfx.common.control.AEntityEditorWithSmsToStudentSupportPanel;
import org.elyeva.rpa102.cltfx.i18n.I18nRpa102Message;
import org.elyeva.rpa102.commons.db.RpaDbConstants;

import es.indra.eplatform.EPException;
import es.indra.eplatform.data.record.IRecord;

public class FirstHourDelayEditorPanel extends AEntityEditorWithSmsToStudentSupportPanel {

	public FirstHourDelayEditorPanel() {
		super(RpaDbConstants.TblFirstHourDelay.VCOL_SMS_NOTIFICATION,
				RpaDbConstants.TblFirstHourDelay.COL_STUDENT_ID);
	}

	@Override
	public Object commit() throws EPException {

		Object firstHourDelayEntity = super.commit();

		sendSmsIfRequired(firstHourDelayEntity);

		return firstHourDelayEntity;
	}

	@Override
	protected String getTranslatedSmsMessage(
			Object firstHourDelayEntity, IRecord studentRecord) {

		return I18nRpa102Message.getString(
				FirstHourDelayConstants.DEF_SMS_MSG_FOR_TRANSLATION,
				new Object [] {studentRecord.getAttribute(RpaDbConstants.TblUsers.COL_NAME)});
	}
}
