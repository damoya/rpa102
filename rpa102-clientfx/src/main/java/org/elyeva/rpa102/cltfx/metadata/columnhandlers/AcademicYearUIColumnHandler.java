package org.elyeva.rpa102.cltfx.metadata.columnhandlers;

import org.elyeva.rpa102.cltfx.metadata.editors.AcademicYearColumnPropertyEditor;

import es.indra.eplatform.data.gui.metadata.UIColumnHandler;

public class AcademicYearUIColumnHandler extends UIColumnHandler {

	/**
	 * Default constructor.
	 */
	public AcademicYearUIColumnHandler() {
		super(AcademicYearColumnPropertyEditor.class.getName());
	}

}
