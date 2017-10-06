package org.elyeva.rpa102.cltfx.classroominc;

import org.elyeva.rpa102.cltfx.common.control.AEntityEditorWithSmsToStudentSupportPanel;
import org.elyeva.rpa102.cltfx.i18n.I18nRpa102Message;
import org.elyeva.rpa102.commons.db.RpaDbConstants;

import es.indra.eplatform.EPException;
import es.indra.eplatform.data.DataUtils;
import es.indra.eplatform.data.gui.metadata.IUIColumn;
import es.indra.eplatform.data.record.IRecord;

public class ClassroomIncidenceEditorPanel extends AEntityEditorWithSmsToStudentSupportPanel {

	public ClassroomIncidenceEditorPanel() {
		super(RpaDbConstants.TblClassroomIncidences.VCOL_SMS_NOTIFICATION,
				RpaDbConstants.TblClassroomIncidences.COL_STUDENT_ID);
	}

	@Override
	public Object commit() throws EPException {

		Object classroomIncidenceEntity = super.commit();

		sendSmsIfRequired(classroomIncidenceEntity);

		return classroomIncidenceEntity;
	}

	@Override
	protected String getTranslatedSmsMessage(
			Object classroomIncidenceEntity, IRecord studentRecord) {

		StringBuilder sb = new StringBuilder(
				I18nRpa102Message.getString(
						ClassroomIncidenceConstants.DEF_SMS_MSG_FOR_TRANSLATION,
						new Object [] {studentRecord.getAttribute(RpaDbConstants.TblUsers.COL_NAME)}));

		for (String causeColumn : RpaDbConstants.TblClassroomIncidences.CAUSES) {

			IUIColumn uiColumn = getUITable().getUIColumn(causeColumn);

			Object value = DataUtils.getValue(causeColumn, classroomIncidenceEntity);

			if (uiColumn != null && value instanceof Boolean && (Boolean) value) {
				sb.append("[");
				sb.append(uiColumn.getText());
				sb.append("]");
			}
		}

		return sb.toString();
	}
}
