package org.elyeva.rpa102.cltfx.metadata.columnhandlers;

import org.elyeva.rpa102.cltfx.metadata.editors.TeacherCollectionComboColumnPropertyEditor;

import es.indra.eplatformfx.data.ui.metadata.columnhandlers.DataCollectionCmbUIColumnHandler;


public class TeacherCollectionCmbUIColumnHandler extends DataCollectionCmbUIColumnHandler {

	public static final String UICOLUMN_PROPTY_INIT_WITH_LOGGED_TEACHER = "initWithLoggedTeacher";

	/**
	 * Default constructor.
	 */
	public TeacherCollectionCmbUIColumnHandler() {
		setPropertyEditorClassName(TeacherCollectionComboColumnPropertyEditor.class.getName());
	}
}
