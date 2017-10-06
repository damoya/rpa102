package org.elyeva.rpa102.cltfx.metadata.editors;

import org.elyeva.rpa102.cltfx.config.RpaConfigConstants;
import org.elyeva.rpa102.cltfx.config.funct.IConfigFunctProvider;

import es.indra.eplatform.EPException;
import es.indra.eplatform.data.gui.metadata.IUIColumn;
import es.indra.eplatform.funct.FunctService;
import es.indra.eplatformfx.commons.beans.editors.IPropertyEditorListener;
import es.indra.eplatformfx.data.ui.form.FormMode;
import es.indra.eplatformfx.data.ui.form.IFormContext;
import es.indra.eplatformfx.data.ui.metadata.editors.DataCollectionComboColumnPropertyEditor;
import es.indra.eplatformfx.data.ui.metadata.editors.IColumnPropertyEditor;
import es.indra.eplatformfx.data.ui.metadata.editors.LabelFieldColumnPropertyEditor;
import es.indra.eplatformfx.data.ui.metadata.editors.PropertyEditorUtils;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;

public class AcademicYearColumnPropertyEditor implements IColumnPropertyEditor<Node, Object> {

	private IColumnPropertyEditor<? extends Node, Object> pe;

	private IUIColumn uiColumn;

	@Override
	public void initialize(IFormContext ctx) throws EPException {

		Object defValue = getDefaultValue();

		if (defValue == null || ctx.getFormMode() == FormMode.FILTER) {
			pe = new DataCollectionComboColumnPropertyEditor();
		}
		else {
			pe = new LabelFieldColumnPropertyEditor();

		}

		pe.setUIColumn(getUIColumn());

		pe.initialize(ctx);

		if (defValue != null && ctx.getFormMode() == FormMode.FILTER) {
			setDefaultValue(defValue);
		}
	}

	@Override
	public void setValue(Object value) throws IllegalArgumentException {
		pe.setValue(value);
	}

	@Override
	public Object getValue() {
		return pe.getValue();
	}

	@Override
	public Node getEditor() {
		return pe.getEditor();
	}

	@Override
	public void addPropertyEditorListener(IPropertyEditorListener<Object> listener) {
		pe.addPropertyEditorListener(listener);
	}

	@Override
	public void removePropertyEditorListener(IPropertyEditorListener<Object> listener) {
		pe.removePropertyEditorListener(listener);
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
	public void initDefaultValue(IFormContext formContext) throws EPException {

		Object defValue = getDefaultValue();

		if (defValue != null) {
			setDefaultValue(defValue);
		}
	}

	private void setDefaultValue(Object defValue) throws EPException {
		setValue(
				getUIColumnHandler().getUIColumnDataConverter().convertToEditor(
						getUIColumn(), defValue));
	}

	private Object getDefaultValue() throws EPException {
		Object defValue = PropertyEditorUtils.getDefaultValue(getUIColumn());

		if (defValue == null) {
			IConfigFunctProvider cfg =
					FunctService.getInstance().getProvider(IConfigFunctProvider.class);

			defValue = cfg.getConfigValue(RpaConfigConstants.PARAM_ID_CURRENT_ACADEMIC_YEAR);
		}

		return defValue;
	}

	@Override
	public ObjectProperty<Object> valueProperty() {
		return pe.valueProperty();
	}
}
