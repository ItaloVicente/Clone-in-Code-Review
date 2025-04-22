package org.eclipse.jface.dialogs;

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
	public static final int NONE = 0;

	public static final int ERROR = 1;

	public static final int INFORMATION = 2;

	public static final int QUESTION = 3;

	public static final int WARNING = 4;

	public static final int CONFIRM = 5;

	public static final int QUESTION_WITH_CANCEL = 6;

	private String[] buttonLabels;

	private Button[] buttons;

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

	public void setImage(int dialogImageType) {
		switch (dialogImageType) {
		case ERROR: {
			this.image = getErrorImage();
			break;
		}
		case INFORMATION: {
			this.image = getInfoImage();
			break;
		}
		case QUESTION:
		case QUESTION_WITH_CANCEL:
		case CONFIRM: {
			this.image = getQuestionImage();
			break;
		}
		case WARNING: {
			this.image = getWarningImage();
			break;
		}
		}
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setButtonLabels(int dialogButtonsType) {
		this.buttonLabels = getButtonLabels(dialogButtonsType);
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
		buttons = new Button[buttonLabels.length];
		for (int i = 0; i < buttonLabels.length; i++) {
			String label = buttonLabels[i];
			Button button = createButton(parent, i, label, defaultButtonIndex == i);
			buttons[i] = button;
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
	protected Button getButton(int index) {
		if (buttons == null || index < 0 || index >= buttons.length) {
			return null;
		}
		return buttons[index];
	}

	protected int getMinimumMessageWidth() {
		return convertHorizontalDLUsToPixels(IDialogConstants.MINIMUM_MESSAGE_AREA_WIDTH);
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

	static String[] getButtonLabels(int kind) {
		String[] dialogButtonLabels;
		switch (kind) {
		case ERROR:
		case INFORMATION:
		case WARNING: {
			dialogButtonLabels = new String[] { IDialogConstants.OK_LABEL };
			break;
		}
		case CONFIRM: {
			dialogButtonLabels = new String[] { IDialogConstants.OK_LABEL, IDialogConstants.CANCEL_LABEL };
			break;
		}
		case QUESTION: {
			dialogButtonLabels = new String[] { IDialogConstants.YES_LABEL, IDialogConstants.NO_LABEL };
			break;
		}
		case QUESTION_WITH_CANCEL: {
			dialogButtonLabels = new String[] { IDialogConstants.YES_LABEL, IDialogConstants.NO_LABEL,
					IDialogConstants.CANCEL_LABEL };
			break;
		}
		default: {
			throw new IllegalArgumentException("Illegal value for kind in MessageDialog"); //$NON-NLS-1$
		}
		}
		return dialogButtonLabels;
	}

	@Override
	protected Button createButton(Composite parent, int id, String label, boolean defaultButton) {
		Button button = super.createButton(parent, id, label, defaultButton);
		if (defaultButton && !customShouldTakeFocus()) {
			button.setFocus();
		}
		return button;
	}

	protected boolean customShouldTakeFocus() {
		if (customArea instanceof Label) {
			return false;
		}
		if (customArea instanceof CLabel) {
			return (customArea.getStyle() & SWT.NO_FOCUS) > 0;
		}
		return true;
	}

	@Override
	public Image getImage() {
		return image;
	}

	protected String[] getButtonLabels() {
		return buttonLabels;
	}

	protected int getDefaultButtonIndex() {
		return defaultButtonIndex;
	}

	protected void setButtons(Button... buttons) {
		if (buttons == null) {
			throw new NullPointerException("The array of buttons cannot be null."); //$NON-NLS-1$
		}
		this.buttons = buttons;
	}

	public void setButtonLabels(String... buttonLabels) {
		if (buttonLabels == null) {
			throw new NullPointerException("The array of button labels cannot be null."); //$NON-NLS-1$
		}
		this.buttonLabels = buttonLabels;
	}

	public void setDefaultButtonIndex(int defaultButtonIndex) {
		this.defaultButtonIndex = defaultButtonIndex;
	}
}
