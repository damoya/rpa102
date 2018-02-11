package org.elyeva.rpa102.cltfx.sms;

import org.elyeva.rpa102.cltfx.i18n.I18nRpa102Message;

import es.indra.eplatform.data.gui.metadata.UIColumn;
import es.indra.eplatform.data.gui.metadata.UIDataRowDef;
import es.indra.eplatform.data.metadata.Column;
import es.indra.eplatform.data.metadata.ColumnOptions;
import es.indra.eplatform.data.metadata.DataRowDef;
import es.indra.eplatform.data.metadata.DataType;

public class StudentPhoneResultHelper {

	public static final String COL_ID = "id";
	public static final String COL_NAME = "name";
	public static final String COL_PHONE = "phone";

	public static final StudentPhoneResultDataRowDef STUDENT_PHONE_RESULT_ENTITY_DEF = new StudentPhoneResultDataRowDef();

	public static final UIStudentPhoneResultDataRowDef UI_STUDENT_PHONE_RESULT_DEF = new UIStudentPhoneResultDataRowDef();

	private static class StudentPhoneResultDataRowDef extends DataRowDef {

		public StudentPhoneResultDataRowDef() {
			super(StudentPhoneResultDataRowDef.class.getName());

			ColumnOptions.Builder builder =
					ColumnOptions.builder(
							"", getName(), COL_ID, DataType.String).order(10);

			addColumn(new Column(builder.build()));

			builder =
					ColumnOptions.builder(
							"", getName(), COL_NAME, DataType.String).order(20);

			addColumn(new Column(builder.build()));

			builder =
					ColumnOptions.builder(
							"", getName(), COL_PHONE, DataType.String).order(30);

			addColumn(new Column(builder.build()));
		}
	}


	private static class UIStudentPhoneResultDataRowDef extends UIDataRowDef {

		public UIStudentPhoneResultDataRowDef() {
			super(STUDENT_PHONE_RESULT_ENTITY_DEF);

			addUIColumn(
					new UIColumn(STUDENT_PHONE_RESULT_ENTITY_DEF.getColumn(COL_NAME))
					.setDisplaySize(250)
					.setText(I18nRpa102Message.getString(
							"UIStudentPhoneResultDataRowDef.column.name.text")));

			addUIColumn(
					new UIColumn(STUDENT_PHONE_RESULT_ENTITY_DEF.getColumn(COL_PHONE))
					.setDisplaySize(100)
					.setText(I18nRpa102Message.getString(
							"UIStudentPhoneResultDataRowDef.column.phone.text")));
		}
	}

}
