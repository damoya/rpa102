package org.elyeva.rpa102.cltfx.sms;


import org.elyeva.rpa102.cltfx.i18n.I18nRpa102Message;
import org.elyeva.rpa102.commons.db.RpaDbConstants;

import es.indra.eplatform.EPException;
import es.indra.eplatform.data.datarow.DataRow;
import es.indra.eplatform.data.datarow.IDataRow;
import es.indra.eplatform.data.datasources.IDataSourceContext;
import es.indra.eplatform.data.gui.metadata.IUIQuery;
import es.indra.eplatform.data.gui.metadata.IUITable;
import es.indra.eplatform.data.gui.metadata.SearchFormDefinition;
import es.indra.eplatform.data.gui.metadata.UIMetadataUtils;
import es.indra.eplatform.data.gui.metadata.UIQueryColumn;
import es.indra.eplatform.data.query.IQuery;
import es.indra.eplatform.data.query.Query;
import es.indra.eplatform.data.query.QueryJoinType;
import es.indra.eplatform.data.query.QueryOptions;
import es.indra.eplatform.util.StringUtils;
import es.indra.eplatform.util.i18n.I18nProvider;
import es.indra.eplatform.util.log.ELogger;
import es.indra.eplatformfx.app.window.IDialog;
import es.indra.eplatformfx.data.ui.CltDataMgmtService;
import es.indra.eplatformfx.data.ui.form.DataFormPanelUtils;
import es.indra.eplatformfx.data.ui.form.ISearchFormPanel;
import es.indra.eplatformfx.data.ui.window.QueryDataSelectorDialogContent;
import javafx.scene.Node;


@I18nProvider(I18nRpa102Message.class)
public class StudentsPhoneSelectorDialogContent extends QueryDataSelectorDialogContent {

	public StudentsPhoneSelectorDialogContent() throws EPException {
		final IDataSourceContext dsCtx =
				CltDataMgmtService.getInstance().getDataSourceContext();

		final IQuery query = createQuery(dsCtx);

		final ISearchFormPanel searchPanel = createSearchFormPanel(dsCtx, query);

		init(dsCtx, query, StudentPhoneResultHelper.UI_STUDENT_PHONE_RESULT_DEF, searchPanel);
	}

	@Override
	protected Node doCreateContent(IDialog dialog) {
		dialog.setTitle(I18nRpa102Message.getString(
				StudentsPhoneSelectorDialogContent.class, "title"));

		Node ret = super.doCreateContent(dialog);

		dialog.setSize(500, 500);

		return ret;
	}

	private IQuery createQuery(IDataSourceContext dsCtx) {
		return new Query(
				QueryOptions.builder(dsCtx.getMetadata())
					.setId(getClass().getName())
					.setRootTable(RpaDbConstants.TblStudents.NAME)
						.addColumn(
								RpaDbConstants.TblStudents.NAME,
								RpaDbConstants.TblStudents.COL_ID)
						.addColumn(
								RpaDbConstants.TblStudents.NAME,
								RpaDbConstants.TblStudents.COL_NAME)
						.addColumn(
								RpaDbConstants.TblStudents.NAME,
								RpaDbConstants.TblStudents.COL_PHONE_1)
						.addColumn(
								RpaDbConstants.TblStudents.NAME,
								RpaDbConstants.TblStudents.COL_PHONE_2)
					.addRelatedTable(RpaDbConstants.TblStudentsByAcademicYear.NAME)
					.addJoin(QueryJoinType.INNER,
							RpaDbConstants.TblStudents.NAME,
							RpaDbConstants.TblStudents.COL_ID,
							RpaDbConstants.TblStudentsByAcademicYear.NAME,
							RpaDbConstants.TblStudentsByAcademicYear.COL_STUDENT_ID)
					.build());
	}


	private ISearchFormPanel createSearchFormPanel(
			IDataSourceContext dsCtx, IQuery query) throws EPException {

		IUIQuery uiQuery = UIMetadataUtils.createUIQuery(query, dsCtx.getUIMetadata());

		IUITable uiStudentsByAcademicYearTable =
				dsCtx.getUIMetadata().getUITable(RpaDbConstants.TblStudentsByAcademicYear.NAME);

		SearchFormDefinition searchFormDefinition = new SearchFormDefinition(uiQuery);
		searchFormDefinition.addUIColumn(
				new UIQueryColumn(
						uiStudentsByAcademicYearTable.getName(),
						uiStudentsByAcademicYearTable.getUIColumn(RpaDbConstants.TblStudentsByAcademicYear.COL_ACADEMIC_YEAR_ID)));
		searchFormDefinition.addUIColumn(
				new UIQueryColumn(
						uiStudentsByAcademicYearTable.getName(),
						uiStudentsByAcademicYearTable.getUIColumn(RpaDbConstants.TblStudentsByAcademicYear.COL_COURSE_ID)));

		uiQuery.addSearchPanelDefinition(searchFormDefinition);

		return DataFormPanelUtils.createDefaultSearchFormPanel(dsCtx, uiQuery);
	}


	@Override
	protected void addDataRow(IDataRow dataRow) {

		String id =
				dataRow.getString(
						RpaDbConstants.TblStudents.NAME + "." + RpaDbConstants.TblStudents.COL_ID);

		String name =
				dataRow.getString(
						RpaDbConstants.TblStudents.NAME + "." + RpaDbConstants.TblStudents.COL_NAME);

		String phone1 =
				dataRow.getString(
						RpaDbConstants.TblStudents.NAME + "." + RpaDbConstants.TblStudents.COL_PHONE_1);

		String phone2 =
				dataRow.getString(
						RpaDbConstants.TblStudents.NAME + "." + RpaDbConstants.TblStudents.COL_PHONE_2);

		if (StringUtils.isNotEmpty(phone1)) {
			DataRow drPhone1 =
					new DataRow(StudentPhoneResultHelper.STUDENT_PHONE_RESULT_ENTITY_DEF);

			drPhone1.setAttribute(
					StudentPhoneResultHelper.COL_ID, id + "." + phone1);
			drPhone1.setAttribute(
					StudentPhoneResultHelper.COL_NAME, name);
			drPhone1.setAttribute(
					StudentPhoneResultHelper.COL_PHONE, phone1);

			super.addDataRow(drPhone1);
		}

		if (StringUtils.isNotEmpty(phone2) && StringUtils.isNotEqual(phone1, phone2)) {
			DataRow drPhone2 =
					new DataRow(StudentPhoneResultHelper.STUDENT_PHONE_RESULT_ENTITY_DEF);

			drPhone2.setAttribute(
					StudentPhoneResultHelper.COL_ID, id + "." + phone2);
			drPhone2.setAttribute(
					StudentPhoneResultHelper.COL_NAME, name);
			drPhone2.setAttribute(
					StudentPhoneResultHelper.COL_PHONE, phone2);

			super.addDataRow(drPhone2);
		}
	}
}
