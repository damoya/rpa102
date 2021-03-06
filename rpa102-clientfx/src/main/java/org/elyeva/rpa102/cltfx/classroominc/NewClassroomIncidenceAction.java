package org.elyeva.rpa102.cltfx.classroominc;

import org.elyeva.rpa102.cltfx.i18n.I18nRpa102Message;
import org.elyeva.rpa102.cltfx.security.Rpa102SecurityConstants;
import org.elyeva.rpa102.commons.db.RpaDbConstants;

import es.indra.eplatformfx.data.ui.actions.NewEntityAction;

public class NewClassroomIncidenceAction extends NewEntityAction {

	/**
	 * Default Constructor.
	 */
	public NewClassroomIncidenceAction() {
		setText(I18nRpa102Message.getString(getClass(), "text"));
		setPermissionId(Rpa102SecurityConstants.CLASSROOM_INCIDENCE_NEW_ENTRY);
		setEntityType(RpaDbConstants.TblClassroomIncidences.NAME);
	}
}
