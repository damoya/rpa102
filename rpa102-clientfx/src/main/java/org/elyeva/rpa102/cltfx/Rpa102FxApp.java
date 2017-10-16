package org.elyeva.rpa102.cltfx;

import org.elyeva.rpa102.cltfx.security.Rpa102SecurityProvider;

import es.indra.eplatformfx.app.AppContext;
import es.indra.eplatformfx.app.Application;
import es.indra.eplatformfx.app.IAppWindow;
import javafx.stage.Stage;

public class Rpa102FxApp extends Application {

	@Override
	protected void startup(Stage stage) {
		IAppWindow appWindow = getAppWindowManager().createAppWindow();

		appWindow.setTitle("RPA102");
		appWindow.setSize(800, 600);
//		appWindow.maximize();
		appWindow.show();
	}

	@Override
	protected final AppContext createApplicationContext(String [] args) {
		return new AppContext(this, args, new Rpa102SecurityProvider());
	}

	public static void main(String [] args) {
		Application.launch(Rpa102FxApp.class, args);
	}
}
