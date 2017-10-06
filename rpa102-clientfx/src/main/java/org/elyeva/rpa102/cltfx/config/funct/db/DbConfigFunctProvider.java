package org.elyeva.rpa102.cltfx.config.funct.db;

import org.elyeva.rpa102.cltfx.Rpa102FxAppConstants;
import org.elyeva.rpa102.cltfx.config.funct.IConfigFunctProvider;
import org.elyeva.rpa102.commons.db.RpaDbConstants;

import es.indra.eplatform.EPException;
import es.indra.eplatform.data.filter.FilterFactory;
import es.indra.eplatform.data.filter.IDataFilter;
import es.indra.eplatform.data.filter.IFilterFactory;
import es.indra.eplatform.data.record.IRecord;
import es.indra.eplatform.data.record.IRecordDataStore;
import es.indra.eplatform.data.record.RecordUtils;
import es.indra.eplatform.util.log.ELogger;
import es.indra.eplatformfx.data.ui.CltDataMgmtService;

public class DbConfigFunctProvider implements IConfigFunctProvider {

	@Override
	public String getConfigValue(String key) throws EPException {
		String ret = null;

		IRecordDataStore recordDataStore = CltDataMgmtService.getInstance().getRecordDataStore();

		IFilterFactory ff = FilterFactory.getInstance();

		IDataFilter filter =
				ff.equals(ff.property(RpaDbConstants.TblConfig.COL_PARAM_ID), ff.literal(key));

		try {
			IRecord record =
					RecordUtils.getFirstRecord(
							recordDataStore.getDataCollection(RpaDbConstants.TblConfig.NAME, filter));
//							RpaDbUtils.getDataStore().getDataCollection(
//									RpaDbConstants.TblConfig.NAME, filter));

			if (record != null) {
				ret = record.getString(RpaDbConstants.TblConfig.COL_VALUE);
			}
		} catch (EPException e) {
			ELogger.error(this, Rpa102FxAppConstants.LOGGER_CATEGORY, e);
		}

		return ret;
	}
}
