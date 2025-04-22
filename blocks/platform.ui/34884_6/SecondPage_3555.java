package org.eclipse.ui.forms.examples.internal.rcp;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.*;
import org.eclipse.ui.forms.examples.internal.ExamplesPlugin;
import org.eclipse.ui.forms.widgets.*;
public class ScrolledPropertiesPage extends FormPage {
	private ScrolledPropertiesBlock block;
	public ScrolledPropertiesPage(FormEditor editor) {
		super(editor, "fourth", "Master Details");
		block = new ScrolledPropertiesBlock(this);
	}
	protected void createFormContent(final IManagedForm managedForm) {
		final ScrolledForm form = managedForm.getForm();
		form.setText("Form with scrolled sections");
		form.setBackgroundImage(ExamplesPlugin.getDefault().getImage(
				ExamplesPlugin.IMG_FORM_BG));
		block.createContent(managedForm);
	}
}
