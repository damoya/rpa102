/*******************************************************************************
 * Name      : es.indra.eplatformfx.data.ui.metadata.editors.MaestroComboColumnPropertyEditor
 * Project   : eplatformfx
 * Subproject: eplatformfx-data-ui
 * CSCI      :
 * CSC       :
 *******************************************************************************
 * Last change info.
 *******************************************************************************
 * #$Author: dmoya $
 * #$Date: 2017-08-18 13:15:56 +0200 (Fri, 18 Aug 2017) $
 * #$Revision: 12005 $
 *******************************************************************************
 */

package org.elyeva.rpa102.cltfx.metadata.editors;

import org.elyeva.rpa102.cltfx.Rpa102FxAppConstants;
import org.elyeva.rpa102.cltfx.metadata.columnhandlers.TeacherCollectionCmbUIColumnHandler;

import es.indra.eplatform.EPException;
import es.indra.eplatform.util.StringUtils;
import es.indra.eplatformfx.app.Application;
import es.indra.eplatformfx.data.ui.form.FormMode;
import es.indra.eplatformfx.data.ui.form.IFormContext;
import es.indra.eplatformfx.data.ui.metadata.editors.DataCollectionComboColumnPropertyEditor;

/**
 *
 */
public class TeacherCollectionComboColumnPropertyEditor extends DataCollectionComboColumnPropertyEditor {

	@Override
	public void initialize(IFormContext ctx) throws EPException {
		super.initialize(ctx);

		if (ctx.getFormMode() == FormMode.NEW || ctx.getFormMode() == FormMode.FILTER) {

			boolean initWithLoggedTeacher =
					getUIColumnHandler().getBooleanValue(
							TeacherCollectionCmbUIColumnHandler.UICOLUMN_PROPTY_INIT_WITH_LOGGED_TEACHER,
							getUIColumn().getBooleanValue(
									TeacherCollectionCmbUIColumnHandler.UICOLUMN_PROPTY_INIT_WITH_LOGGED_TEACHER, false));

			if (initWithLoggedTeacher) {
				String teacherId =
						Application.getInstance().getContext().getProperty(
								Rpa102FxAppConstants.APPCTX_PPRTY_LOGGED_TEACHER);

				if (!StringUtils.isEmpty(teacherId)) {
					setValue(teacherId);
				}
			}
		}
	}
}
