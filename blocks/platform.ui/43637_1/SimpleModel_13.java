package org.eclipse.ui.forms.examples.internal.rcp;
import org.eclipse.ui.forms.examples.internal.FormEditorInput;

public class SimpleFormEditorInput extends FormEditorInput {
	private SimpleModel model;

	public SimpleFormEditorInput(String name) {
		super(name);
		model = new SimpleModel();
	}

	public SimpleModel getModel() {
		return model;
	}
}
