package org.eclipse.jface.dialogs;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class PlainMessageDialog extends IconAndMessageDialog {

	private List<String> buttonLabels;
	private int defaultButtonIndex;
	private String title;
	private Image titleImage;
	private Image image = null;
	private Control customArea;

	public PlainMessageDialog(Shell parentShell, String dialogTitle) {
		super(parentShell);
		this.title = dialogTitle;
	}

	public void setTitleImage(Image titleImage) {
		this.titleImage = titleImage;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setButtonLabels(List<String> labels) {
		this.buttonLabels = labels;
	}

	@Override
	protected void buttonPressed(int buttonId) {
		setReturnCode(buttonId);
		close();
	}

	@Override
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		if (title != null) {
			shell.setText(title);
		}
		if (titleImage != null) {
			shell.setImage(titleImage);
		}
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		for (int id = 0; id < buttonLabels.size(); id++) {
			createButton(parent, id, buttonLabels.get(id), defaultButtonIndex == id);
		}
	}

	protected Control createCustomArea(Composite parent) {
		return null;
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		createMessageArea(parent);
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		composite.setLayout(layout);
		GridData data = new GridData(GridData.FILL_BOTH);
		data.horizontalSpan = 2;
		composite.setLayoutData(data);
		customArea = createCustomArea(composite);
		if (customArea == null) {
			customArea = new Label(composite, SWT.NULL);
		}
		return composite;
	}

	@Override
	protected void handleShellCloseEvent() {
		super.handleShellCloseEvent();
		setReturnCode(SWT.DEFAULT);
	}

	public int open(int style) {
		style &= SWT.SHEET;
		this.setShellStyle(this.getShellStyle() | style);
		return this.open();
	}

	@Override
	protected Button createButton(Composite parent, int id, String label, boolean defaultButton) {
		Button button = super.createButton(parent, id, label, defaultButton);
		if (defaultButton && !customShouldTakeFocus()) {
			button.setFocus();
		}
		return button;
	}

	private boolean customShouldTakeFocus() {
		if (customArea instanceof Label) {
			return false;
		}
		if (customArea instanceof CLabel) {
			return (customArea.getStyle() & SWT.NO_FOCUS) > 0;
		}
		return true;
	}

	public void setDefaultButtonIndex(int defaultButtonIndex) {
		this.defaultButtonIndex = defaultButtonIndex;
	}

	@Override
	protected Image getImage() {
		return image;
	}
}
