package org.elyeva.rpa102.cltfx.firsthourdelay;

import org.elyeva.rpa102.cltfx.i18n.I18nRpa102Message;
import org.elyeva.rpa102.cltfx.security.Rpa102SecurityConstants;
import org.elyeva.rpa102.commons.db.RpaDbConstants;

import es.indra.eplatformfx.data.ui.actions.NewEntityAction;

public class NewFirstHourDelayEntryAction extends NewEntityAction {

	/**
	 * Default Constructor.
	 */
	public NewFirstHourDelayEntryAction() {
		setText(I18nRpa102Message.getString(getClass(), "text"));
		setPermissionId(Rpa102SecurityConstants.FIRST_HOUR_DELAY_NEW_ENTRY);
		setEntityType(RpaDbConstants.TblFirstHourDelay.NAME);
//		setDialogContentClass(FirstHourDelayDlgContent.class);
	}
}
