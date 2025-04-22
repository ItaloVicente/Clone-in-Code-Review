package org.eclipse.e4.ui.about;

import org.eclipse.jface.dialogs.DialogPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

public abstract class InstallationPage extends DialogPage {

	private IInstallationPageContainer container;

	@Override
	public void setMessage(String newMessage) {
		super.setMessage(newMessage);
	}

	@Override
	public void setMessage(String newMessage, int newType) {
		super.setMessage(newMessage, newType);
	}

	public void setPageContainer(IInstallationPageContainer container) {
		this.container = container;
	}

	public void createPageButtons(Composite parent) {
	}

	protected Button createButton(Composite parent, int id, String label) {
		Button button = new Button(parent, SWT.PUSH);
		button.setText(label);
		button.setData(new Integer(id));
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				buttonPressed(((Integer) event.widget.getData()).intValue());
			}
		});
		container.registerPageButton(this, button);
		return button;
	}

	protected void buttonPressed(int buttonId) {
	}

	protected IInstallationPageContainer getPageContainer() {
		return container;
	}

}
