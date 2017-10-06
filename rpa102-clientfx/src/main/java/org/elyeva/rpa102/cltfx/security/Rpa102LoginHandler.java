package org.elyeva.rpa102.cltfx.security;

import org.elyeva.rpa102.cltfx.Rpa102FxAppConstants;
import org.elyeva.rpa102.commons.db.RpaDbConstants;

import es.indra.eplatform.EPException;
import es.indra.eplatform.data.IDataStore;
import es.indra.eplatform.data.filter.FilterFactory;
import es.indra.eplatform.data.filter.IDataFilter;
import es.indra.eplatform.data.filter.IFilterFactory;
import es.indra.eplatform.data.record.IRecord;
import es.indra.eplatform.data.record.RecordUtils;
import es.indra.eplatform.util.log.ELogger;
import es.indra.eplatformfx.app.xtras.login.ALoginHandler;

public class Rpa102LoginHandler extends ALoginHandler {

	private IDataStore<IRecord> dataStore;

	public Rpa102LoginHandler(IDataStore<IRecord> dataStore) {
		this.dataStore = dataStore;
	}

	@Override
	protected boolean doValidateUser(String user, String pwd) {
		return (getUserRecord(user, pwd) != null);
	}

	@Override
	protected boolean doChangePasswordAndValidateUser(String user, String currentPwd, String newPwd) {

		boolean ret = false;

		IRecord record = getUserRecord(user, currentPwd);

		if (record == null) {
			setValidationMessage("Invalid user password");
		}
		else {
			IRecord newRecord = record.clone();

			newRecord.setAttribute(RpaDbConstants.TblUsers.COL_PASSWORD, newPwd);

			try {
				dataStore.update(record, newRecord);

				ret = true;
			} catch (EPException e) {
				setValidationMessage("Error updating new password");

				ELogger.error(this, Rpa102FxAppConstants.LOGGER_CATEGORY, e);
			}
		}


		return ret;
	}

	private IRecord getUserRecord(String user, String pwd) {

		IRecord record = null;

		IFilterFactory ff = FilterFactory.getInstance();

		IDataFilter filter = ff.and(
				ff.equals(ff.property(RpaDbConstants.TblUsers.COL_USER_ID), ff.literal(user)),
				ff.equals(ff.property(RpaDbConstants.TblUsers.COL_PASSWORD), ff.literal(pwd)));

		try {
			record = RecordUtils.getFirstRecord(
							dataStore.getDataCollection(RpaDbConstants.TblUsers.NAME, filter));

		} catch (EPException e) {
			ELogger.error(this, Rpa102FxAppConstants.LOGGER_CATEGORY, e);
		}

		return record;
	}

	@Override
	public boolean isChangePasswordSupported() {
		return false;
	}
}
