package org.elyeva.rpa102.cltfx.metadata.columnhandlers;

import org.elyeva.rpa102.cltfx.metadata.editors.SmsColumnPropertyEditor;

import es.indra.eplatform.data.gui.metadata.UIColumnHandler;

public class SmsUIColumnHandler extends UIColumnHandler {

	/**
	 * Default constructor.
	 */
	public SmsUIColumnHandler() {
		super(SmsColumnPropertyEditor.class.getName());
	}

}
