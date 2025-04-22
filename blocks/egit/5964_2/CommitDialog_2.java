package org.eclipse.egit.ui.internal.components;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

public class ToggleableWarningLabel extends Composite {

	private Label warningText;

	public ToggleableWarningLabel(Composite parent, int style) {
		super(parent, style);

		setVisible(false);
		setLayout(new GridLayout(2, false));

		Label image = new Label(this, SWT.NONE);
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.BEGINNING)
				.applyTo(image);
		image.setImage(PlatformUI.getWorkbench().getSharedImages()
				.getImage(ISharedImages.IMG_OBJS_WARN_TSK));

		warningText = new Label(this, SWT.WRAP);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(warningText);
	}

	public void showMessage(String message) {
		warningText.setText(message);
		layout(true);

		changeVisibility(true);
	}

	public void hideMessage() {
		if (isVisible())
			changeVisibility(false);
	}

	private void changeVisibility(boolean visible) {
		setVisible(visible);
		GridData data = (GridData) getLayoutData();
		data.exclude = !visible;
		getParent().layout();
	}
}
