
package org.eclipse.jface.tests.wizards;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class TheTestWizardPage extends WizardPage {
	public static final String BAD_TEXT_FIELD_CONTENTS = "BAD VALUE";
	public static final String BAD_TEXT_FIELD_STATUS = "A bad value was entered";
	public static final String GOOD_TEXT_FIELD_CONTENTS = "GOOD VALUE";
	public Text textInputField;
	private boolean throwExceptionOnDispose; 

	public TheTestWizardPage(String name) {
		super(name);
	}

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 2;
		layout.verticalSpacing = 9;
		Label label = new Label(container, SWT.NULL);
		label.setText(getName());

		textInputField = new Text(container, SWT.BORDER | SWT.SINGLE);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		textInputField.setLayoutData(gd);
		textInputField.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});
		
		initialize();
		dialogChanged();
		setControl(container);
	}

	private void initialize() {}


	private void dialogChanged() {
		if (textInputField.getText().equals(BAD_TEXT_FIELD_CONTENTS)) {
			setPageComplete(false);
			updateStatus(BAD_TEXT_FIELD_STATUS);
			return;
		}
		setPageComplete(true);
		updateStatus(null);
	}

	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}
	
	public void setThrowExceptionOnDispose(boolean throwExceptionOnDispose) {
		this.throwExceptionOnDispose = throwExceptionOnDispose;
	}
	
	@Override
	public void dispose() {
		super.dispose();
		if(throwExceptionOnDispose)
			throw new NullPointerException();
	}

}
