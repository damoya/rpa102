package org.elyeva.rpa102.cltfx.common.control;

import org.elyeva.rpa102.cltfx.sms.SmsConfig;
import org.elyeva.rpa102.cltfx.sms.SmsDialogContent;
import org.elyeva.rpa102.cltfx.sms.funct.SmsState;
import org.elyeva.rpa102.commons.db.RpaDbConstants;
import org.elyeva.rpa102.commons.domain.IPhoneNumber;
import org.elyeva.rpa102.commons.domain.impl.PhoneNumber;

import es.indra.eplatform.EPException;
import es.indra.eplatform.data.DataUtils;
import es.indra.eplatform.data.IDataCollection;
import es.indra.eplatform.data.record.IRecord;
import es.indra.eplatform.data.record.RecordUtils;
import es.indra.eplatform.util.StringUtils;
import es.indra.eplatformfx.app.Application;
import es.indra.eplatformfx.app.utils.FXUtils;
import es.indra.eplatformfx.app.window.IDialog;
import es.indra.eplatformfx.data.ui.form.DefaultEntityEditorPanel;
import es.indra.eplatformfx.data.ui.form.FormMode;

public abstract class AEntityEditorWithSmsToStudentSupportPanel extends DefaultEntityEditorPanel {

	private String entitySmsNotificationFieldName;

	private String entityStudentIdFieldName;

	protected AEntityEditorWithSmsToStudentSupportPanel(
			String entitySmsNotificationFieldName, String entityStudentIdFieldName) {
		this.entitySmsNotificationFieldName = entitySmsNotificationFieldName;
		this.entityStudentIdFieldName = entityStudentIdFieldName;
	}

	protected void sendSmsIfRequired(Object entity) throws EPException {

		if (getFormContext().getFormMode() == FormMode.NEW) {

			Object smsStateValue =
					getFormContext().getEditorValue(entitySmsNotificationFieldName);

			if (smsStateValue != null
					&& SmsState.valueOf(smsStateValue.toString()) == SmsState.SENT_REQUEST) {
				sendSms(entity);
			}
		}
	}

	private void sendSms(Object entity) throws EPException {
		final IRecord studentRecord = getRelatedStudentRecord(entity);

		final SmsConfig smsConfig = createSmsConfig(entity, studentRecord);

		IDialog dialog =
				Application.getInstance().getWindowManager().createDialog(
						FXUtils.getIWindowAncestor(this),
						new SmsDialogContent(smsConfig), true);

		dialog.showAndWait();
	}

	private IRecord getRelatedStudentRecord(Object entity) throws EPException {

		IDataCollection<IRecord> records =
				RecordUtils.getRelatedRecordsByFk(
						getRecordDataStore(),
						getUITable().getTable(),
						entity,
						entityStudentIdFieldName);

		return RecordUtils.getFirstRecord(records);
	}

	private SmsConfig createSmsConfig(Object entity, IRecord studentRecord) {

		final SmsConfig smsConfig = new SmsConfig();

		if (studentRecord != null) {
			smsConfig.msg(getTranslatedSmsMessage(entity, studentRecord));

			addPhoneNumber(smsConfig, studentRecord, RpaDbConstants.TblUsers.COL_PHONE_1);
			addPhoneNumber(smsConfig, studentRecord, RpaDbConstants.TblUsers.COL_PHONE_2);

			smsConfig.referencedElement(
					DataUtils.getStringKey(entity, getUITable().getTable(), true));
		}

		return smsConfig;
	}

	private IPhoneNumber addPhoneNumber(
			SmsConfig smsConfig, IRecord studentRecord, String phoneColName) {

		IPhoneNumber ret = null;

		final String value = studentRecord.getString(phoneColName);

		if (StringUtils.isNotEmpty(value)) {
			ret = new PhoneNumber(value, phoneColName);
		}

		if (ret != null) {
			smsConfig.addDestination(ret);
		}

		return ret;
	}

	protected abstract String getTranslatedSmsMessage(Object entity, IRecord studentRecord);
}
