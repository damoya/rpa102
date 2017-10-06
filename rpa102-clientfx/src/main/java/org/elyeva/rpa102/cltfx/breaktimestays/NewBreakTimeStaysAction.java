package org.elyeva.rpa102.cltfx.breaktimestays;

import org.elyeva.rpa102.cltfx.i18n.I18nRpa102Message;
import org.elyeva.rpa102.cltfx.security.Rpa102SecurityConstants;
import org.elyeva.rpa102.commons.db.RpaDbConstants;

import es.indra.eplatformfx.data.ui.actions.NewEntityAction;

public class NewBreakTimeStaysAction extends NewEntityAction {

	/**
	 * Default Constructor.
	 */
	public NewBreakTimeStaysAction() {
		setText(I18nRpa102Message.getString(getClass(), "text"));
		setPermissionId(Rpa102SecurityConstants.BREAK_TIME_STAYS_NEW_ENTRY);
		setEntityType(RpaDbConstants.TblBreakTimeStays.NAME);
	}
}
