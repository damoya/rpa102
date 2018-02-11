package org.elyeva.rpa102.cltfx.sms;

import java.util.ArrayList;
import java.util.List;

import org.elyeva.rpa102.cltfx.Rpa102FxAppConstants;
import org.elyeva.rpa102.cltfx.i18n.I18nRpa102Message;
import org.elyeva.rpa102.cltfx.sms.funct.ISmsSenderFunctProvider;

import es.indra.eplatform.EPException;
import es.indra.eplatform.data.datarow.IDataRow;
import es.indra.eplatform.data.datasources.IDataSourceContext;
import es.indra.eplatform.funct.FunctService;
import es.indra.eplatform.util.i18n.I18nProvider;
import es.indra.eplatform.util.log.ELogger;
import es.indra.eplatformfx.app.Application;
import es.indra.eplatformfx.app.actions.AAction;
import es.indra.eplatformfx.app.actions.ActionMethod;
import es.indra.eplatformfx.app.control.AppToolBar;
import es.indra.eplatformfx.app.menu.AppMenuDefinition;
import es.indra.eplatformfx.app.tasks.TaskListExecutorDialogContent;
import es.indra.eplatformfx.app.utils.FXUtils;
import es.indra.eplatformfx.app.window.ADialogContent;
import es.indra.eplatformfx.app.window.DialogUtils;
import es.indra.eplatformfx.app.window.IDialog;
import es.indra.eplatformfx.commons.forms.FormPanel;
import es.indra.eplatformfx.data.ui.CltDataMgmtService;
import es.indra.eplatformfx.data.ui.datatable.DataTableViewCleanAction;
import es.indra.eplatformfx.data.ui.datatable.QueryDataTableView;
import es.indra.eplatformfx.data.ui.datatable.DataTableViewRemoveSelectedAction;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

@I18nProvider(I18nRpa102Message.class)
public class SmsToManyStudentsDialogContent extends ADialogContent<Node> {

	private QueryDataTableView<IDataRow> selectedStudentsTable = new QueryDataTableView<>();

	private TextArea taMsg;

	private String from = "RPA";

	private Label lblMsgLength;

	/**
	 * Default constructor.
	 */
	public SmsToManyStudentsDialogContent() {
		// empty method
	}


	@Override
	protected Node doCreateContent(IDialog dialog) {

		dialog.setSize(500, 500);
		dialog.setResizable(false);

		BorderPane selectedStudentsContent = new BorderPane();
		selectedStudentsContent.setMinHeight(180);
		selectedStudentsContent.setPrefHeight(250);
		selectedStudentsContent.setMaxHeight(350);
		selectedStudentsContent.setTop(createSelectedStudentsToolBar());
		selectedStudentsContent.setCenter(selectedStudentsTable);

		try {
			IDataSourceContext dsCtx =
					CltDataMgmtService.getInstance().getDataSourceContext();

			selectedStudentsTable.createColumns(
					dsCtx, StudentPhoneResultHelper.UI_STUDENT_PHONE_RESULT_DEF);
		} catch (EPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//

		FormPanel formPanel = new FormPanel(1, FormPanel.TYPE_TOP_LABEL);

		taMsg = new TextArea();
		taMsg.setWrapText(true);
		taMsg.setMinHeight(100);
		taMsg.setPrefHeight(100);
		taMsg.setMaxHeight(220);
		taMsg.textProperty().addListener(
				(observable, oldValue, newValue) -> {
					showLengthInfo(newValue.length());
				});

		lblMsgLength = new Label();

		formPanel.addCmp(selectedStudentsContent, FormPanel.LONG_WIDTH);
		formPanel.addCmp(
				I18nRpa102Message.getString(SmsToManyStudentsDialogContent.class, "label.message"),
				taMsg);
		formPanel.hAlignment(HPos.RIGHT).addLbl(lblMsgLength);

		showLengthInfo(0);

		return formPanel;
	}

	@Override
	protected void doDestroyContent() {
		// empty method
	}

	private void showLengthInfo(int textLength) {
		int numMsgs = (textLength == 0 ? 0 : (textLength / 160) + 1);

		lblMsgLength.setText(
				I18nRpa102Message.getString(
						SmsToManyStudentsDialogContent.class, "label.msgLength",
						new Object [] { textLength, numMsgs}));
	}

	@ActionMethod(order=100, text="cancel")
	public void cancel() {
		close();
	}

	@ActionMethod(order=1, text="sendMessage")
	public void sendMessage() {
		List<IDataRow> destinations = selectedStudentsTable.getEntities();

		if (destinations.isEmpty()) {
			DialogUtils.showError(
					"Error", "Destinatarios del SMS",
					"Es necesario seleccionar los destinatarios del mensaje");

			return;
		}
		else {
			ISmsSenderFunctProvider provider =
					FunctService.getInstance().getImplementation(ISmsSenderFunctProvider.class);

			final String msg = taMsg.getText();

			List<SendSmsTaskItem> taskItems = new ArrayList<>();

			for (IDataRow destination : destinations) {
				final String name = destination.getString(StudentPhoneResultHelper.COL_NAME);
				String phone = destination.getString(StudentPhoneResultHelper.COL_PHONE);

				taskItems.add(
						new SendSmsTaskItem("Envio de SMS a " + name, provider, from, phone, msg));
			}

			TaskListExecutorDialogContent executorDialog =
					new TaskListExecutorDialogContent(
							"Envio de SMS a Alumnos", "Enviando mensajes...", taskItems);

     		IDialog dialog =
     				Application.getInstance().getWindowManager().createDialog(
     						Application.getInstance().getActiveAppWindow(), executorDialog, true);

     		dialog.show();

     		executorDialog.execute();

     		selectedStudentsTable.clear();
		}
	}

	private AppToolBar createSelectedStudentsToolBar() {

		AppToolBar selectedStudentsToolBar = new AppToolBar();

		Region spacer = new Region();
		HBox.setHgrow(spacer, Priority.ALWAYS);
		selectedStudentsToolBar.getItems().add(spacer);

        AppMenuDefinition appMenuDef = new AppMenuDefinition();
        appMenuDef.add(new AAction(
        		I18nRpa102Message.getString(
        				SmsToManyStudentsDialogContent.class, "action.searchDestinations")){
            @Override
            public void handle(ActionEvent event) {

            	try {
	            	StudentsPhoneSelectorDialogContent dlgContent =
	            			new StudentsPhoneSelectorDialogContent();

	                IDialog dlg =
	                		Application.getInstance().getWindowManager().createDialog(
	                				FXUtils.getIWindowAncestor(SmsToManyStudentsDialogContent.this),
	                				dlgContent, true);

	                dlg.showAndWait();

	                selectedStudentsTable.addEntities(dlgContent.getSelectedRecords());
            	}
            	catch(EPException e) {
            		ELogger.error(this, Rpa102FxAppConstants.LOGGER_CATEGORY, e);
            	}
        	}
        });

        appMenuDef.add(new DataTableViewRemoveSelectedAction());
        appMenuDef.add(new DataTableViewCleanAction());

        selectedStudentsToolBar.installMenus(appMenuDef, selectedStudentsTable);

        return selectedStudentsToolBar;
	}
}
