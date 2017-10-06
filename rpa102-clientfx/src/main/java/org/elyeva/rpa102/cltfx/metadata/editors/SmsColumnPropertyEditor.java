package org.elyeva.rpa102.cltfx.metadata.editors;

import org.elyeva.rpa102.cltfx.sms.funct.ISmsSenderFunctProvider;
import org.elyeva.rpa102.cltfx.sms.funct.SmsState;

import es.indra.eplatform.EPException;
import es.indra.eplatform.data.gui.metadata.IUIColumn;
import es.indra.eplatform.funct.FunctService;
import es.indra.eplatform.util.listeners.ListenerManager;
import es.indra.eplatformfx.commons.beans.editors.IPropertyEditorListener;
import es.indra.eplatformfx.data.ui.form.FormMode;
import es.indra.eplatformfx.data.ui.form.IFormContext;
import es.indra.eplatformfx.data.ui.metadata.editors.BooleanColumnPropertyEditor;
import es.indra.eplatformfx.data.ui.metadata.editors.IColumnPropertyEditor;
import es.indra.eplatformfx.data.ui.metadata.editors.LabelFieldColumnPropertyEditor;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;

public class SmsColumnPropertyEditor implements IColumnPropertyEditor<Node, String> {

	private ObjectProperty<String> valueProp = new SimpleObjectProperty<>();

	private LabelFieldColumnPropertyEditor labelColumnPrptyEditor;

	private BooleanColumnPropertyEditor booleanColumnPrptyEditor;

	private BooleanPrptEditorListenerAdapter listenerAdapter;

	private IUIColumn uiColumn;

	@Override
	public void initialize(IFormContext ctx) throws EPException {

		if (ctx.getFormMode() == FormMode.NEW) {
			booleanColumnPrptyEditor = new BooleanColumnPropertyEditor();

			booleanColumnPrptyEditor.getEditor().setText("Enviar SMS");

			booleanColumnPrptyEditor.setUIColumn(getUIColumn());
			booleanColumnPrptyEditor.initialize(ctx);
		}
		else {
			labelColumnPrptyEditor = new LabelFieldColumnPropertyEditor();
			labelColumnPrptyEditor.setUIColumn(getUIColumn());
			labelColumnPrptyEditor.initialize(ctx);

			labelColumnPrptyEditor.getEditor().setText("ppppppp");

			ISmsSenderFunctProvider provider =
					FunctService.getInstance().getImplementation(ISmsSenderFunctProvider.class);

//			IRecord smsRecord = provider.getSmsRecordByRefElement(refElementId);
		}
	}

	@Override
	public void setValue(String value) throws IllegalArgumentException {
		this.valueProp.set(value);

		if (labelColumnPrptyEditor != null) {
			labelColumnPrptyEditor.setValue(value);
			labelColumnPrptyEditor.setValue("Pdte");
		}
	}

	@Override
	public String getValue() {

		String ret = valueProp.get();

		if (booleanColumnPrptyEditor != null) {
			if (booleanColumnPrptyEditor.getValue() != null && booleanColumnPrptyEditor.getValue()) {
				ret = SmsState.SENT_REQUEST.name();
			}
			else {
				ret = null;
			}
		}

		return ret;
	}

	@Override
	public Node getEditor() {
		return booleanColumnPrptyEditor == null
				? labelColumnPrptyEditor.getEditor()
				: booleanColumnPrptyEditor.getEditor();
	}

	@Override
	public void addPropertyEditorListener(IPropertyEditorListener<String> listener) {
		if (booleanColumnPrptyEditor != null) {
			if (listenerAdapter == null) {
				listenerAdapter = new BooleanPrptEditorListenerAdapter();

				booleanColumnPrptyEditor.addPropertyEditorListener(listenerAdapter);
			}

			listenerAdapter.addListener(listener);
		}
	}

	@Override
	public void removePropertyEditorListener(IPropertyEditorListener<String> listener) {
		if (booleanColumnPrptyEditor != null && listenerAdapter != null) {
			listenerAdapter.removeListener(listener);

			if (listenerAdapter.getListeners().isEmpty()) {
				booleanColumnPrptyEditor.removePropertyEditorListener(listenerAdapter);

				listenerAdapter = null;
			}
		}
	}

	@Override
	public void setUIColumn(IUIColumn uiColumn) {
		this.uiColumn = uiColumn;
	}

	@Override
	public IUIColumn getUIColumn() {
		return uiColumn;
	}

	@Override
	public ObjectProperty<String> valueProperty() {
		return valueProp;
	}


	public class BooleanPrptEditorListenerAdapter extends ListenerManager<IPropertyEditorListener<String>> implements IPropertyEditorListener<Boolean> {

		@Override
		public void onCommitValue(Boolean oldValue, Boolean newValue) {

			String oldValueStr = oldValue == null ? null : String.valueOf(oldValue);
			String newValueStr = newValue == null ? null : String.valueOf(newValue);

			for (IPropertyEditorListener<String> listener : getListeners()) {
				listener.onCommitValue(oldValueStr, newValueStr);
			}
		}

		@Override
		public void onCancelEdition() {
			for (IPropertyEditorListener<String> listener : getListeners()) {
				listener.onCancelEdition();
			}
		}

		@Override
		public void onFinishEdition(Boolean newValue, boolean moveToNextEditor) {

			String newValueStr = newValue == null ? null : String.valueOf(newValue);

			for (IPropertyEditorListener<String> listener : getListeners()) {
				listener.onFinishEdition(newValueStr, moveToNextEditor);
			}

		}
	}
}
