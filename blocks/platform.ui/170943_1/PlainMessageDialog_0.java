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

	private final String title;
	private final Image titleImage;
	private final Image image;
	private final List<String> buttonLabels;
	private final int defaultButtonIndex;
	private Control customArea;

	public PlainMessageDialog(Shell parentShell, String dialogTitle, PlainMessageDialogAppearance appearance) {
		super(parentShell);

		this.title = dialogTitle;
		this.titleImage = appearance.getTitleImage();
		this.image = appearance.getImage();
		this.message = appearance.getMessage();
		this.buttonLabels = appearance.getButtonLabels();
		this.defaultButtonIndex = appearance.getDefaultButtonIndex();
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
		Label dummyLabelForSpacingPurposes = new Label(parent, SWT.NULL);
		return dummyLabelForSpacingPurposes;
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

	@Override
	protected Image getImage() {
		return image;
	}
}
