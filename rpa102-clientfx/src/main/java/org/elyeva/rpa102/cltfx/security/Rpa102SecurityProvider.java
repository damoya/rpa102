package org.elyeva.rpa102.cltfx.security;

import org.elyeva.rpa102.cltfx.Rpa102FxAppConstants;
import org.elyeva.rpa102.cltfx.i18n.I18nRpa102Message;
import org.elyeva.rpa102.commons.db.RpaDbConstants;

import es.indra.eplatform.EPException;
import es.indra.eplatform.app.IAppContext;
import es.indra.eplatform.app.security.ASecurityProvider;
import es.indra.eplatform.app.security.AuthenticatedUser;
import es.indra.eplatform.app.security.IAuthenticatedUser;
import es.indra.eplatform.app.security.SecurityException;
import es.indra.eplatform.data.IDataCollection;
import es.indra.eplatform.data.datasources.DataSourceDefinition;
import es.indra.eplatform.data.datasources.sql.SQLDataSource;
import es.indra.eplatform.data.filter.FilterFactory;
import es.indra.eplatform.data.filter.IDataFilter;
import es.indra.eplatform.data.filter.IFilterFactory;
import es.indra.eplatform.data.record.IRecord;
import es.indra.eplatform.data.record.IRecordDataStore;
import es.indra.eplatform.data.record.RecordDataStore;
import es.indra.eplatform.data.record.RecordUtils;
import es.indra.eplatform.services.ServiceEngine;
import es.indra.eplatform.util.StringUtils;
import es.indra.eplatform.util.log.ELogger;
import es.indra.eplatformfx.app.Application;
import es.indra.eplatformfx.app.window.IDialog;
import es.indra.eplatformfx.app.xtras.login.ILoginHandler;
import es.indra.eplatformfx.app.xtras.login.LoginDialogContent;

public class Rpa102SecurityProvider extends ASecurityProvider {

	private static IRecordDataStore recordDataStore = null;

	@Override
	public final void initialize(IAppContext appCtx) throws SecurityException {

		try {
			try {
				recordDataStore = getRecordDataStore();
			} catch (EPException e) {
				throw new SecurityException(e.getMessage(), e);
			}

			initPermissionDefinitions(appCtx);

			ILoginHandler loginHandler = new Rpa102LoginHandler(recordDataStore);

			IDialog loginDialog =
					Application.getInstance().getWindowManager().createDialog(
							new LoginDialogContent(loginHandler), true);

			loginDialog.setTitle(I18nRpa102Message.getString("LoginWindow.title"));

			loginDialog.showAndWait();

			if (loginHandler.isLogged()) {
				setAuthenticatedUser(new AuthenticatedUser(loginHandler.getLoggedUser()));

				setLoggedTeacher(getAuthenticatedUser(), appCtx);

				setUserPermissions(getAuthenticatedUser(), appCtx);
			}
			else {
				throw new SecurityException();
			}
		}
		finally {
			if (recordDataStore != null) {
				try {
					recordDataStore.getDataSource().close();
				} catch (EPException e) {
					ELogger.error(this, Rpa102FxAppConstants.LOGGER_CATEGORY, e);
				}
			}
		}
	}


	private void setLoggedTeacher(
			IAuthenticatedUser authUser, IAppContext appCtx) throws SecurityException {

		IFilterFactory ff = FilterFactory.getInstance();

		IDataFilter filter =
				ff.equals(ff.property(
						RpaDbConstants.TblUsers.COL_USER_ID), ff.literal(authUser.getUserId()));

		try {
			IRecord userRecord =
					RecordUtils.getFirstRecord(
							getRecordDataStore().getDataCollection(
									RpaDbConstants.TblUsers.NAME, filter));

			if (userRecord != null) {
				String teacherId = userRecord.getString(RpaDbConstants.TblUsers.COL_TEACHER_ID);

				if (!StringUtils.isEmpty(teacherId)) {
					appCtx.setProperty(Rpa102FxAppConstants.APPCTX_PPRTY_LOGGED_TEACHER, teacherId);
				}
			}
		}
		catch (EPException e) {
			throw new SecurityException(e.getMessage(), e);
		}
	}


	protected void setUserPermissions(
			IAuthenticatedUser authUser, IAppContext appCtx) throws SecurityException {
		IDataCollection<IRecord> dcUserRole = null;

		IDataCollection<IRecord> dcRolePermission = null;

		try {
			dcUserRole =
					getRecordDataStore().getDataCollection(
							RpaDbConstants.TblUserRole.NAME,
							RpaDbConstants.TblUserRole.COL_USER_ID + "='" + authUser.getUserId() + "'");

			for (IRecord recUserRole : dcUserRole) {
				final String roleId =
						recUserRole.getString(RpaDbConstants.TblUserRole.COL_ROLE_ID);

				dcRolePermission =
						getRecordDataStore().getDataCollection(
								RpaDbConstants.TblRolePermissions.NAME,
								RpaDbConstants.TblRolePermissions.COL_ROLE_ID + "='" + roleId + "'");

				for (IRecord recRolePermission : dcRolePermission) {
					getPermissionManager().setAvailable(recRolePermission.getString(
							RpaDbConstants.TblRolePermissions.COL_PERMISSION_ID), true);
				}
			}
			//

		} catch (EPException e) {
			throw new SecurityException(e.getMessage(), e);
		}
		finally {
			if (dcUserRole != null) {
				dcUserRole.close();
			}

			if (dcRolePermission != null) {
				dcRolePermission.close();
			}
		}
	}

	protected void initPermissionDefinitions(IAppContext appCtx) throws SecurityException {
		getPermissionManager().register(Rpa102SecurityConstants.permissions);
	}

	public synchronized IRecordDataStore getRecordDataStore() throws EPException {

		if (recordDataStore == null) {
			DataSourceDefinition dsDef = new DataSourceDefinition(SQLDataSource.class);

			dsDef.setProperty(
					SQLDataSource.JDBC_DRIVER_CLASS_PROPERTY,
					ServiceEngine.getInstance().getContext().getProperty("db.jdbcDriverClass"));

			dsDef.setProperty(
					SQLDataSource.JDBC_URL_PROPERTY,
					ServiceEngine.getInstance().getContext().getProperty("db.jdbcURL"));

			SQLDataSource ds = (SQLDataSource) dsDef.createDataSource();

			recordDataStore = new RecordDataStore(ds.createDataStore());
		}

		return recordDataStore;
	}
}
