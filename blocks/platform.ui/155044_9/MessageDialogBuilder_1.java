package org.eclipse.jface.dialogs;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;

public class MessageDialogBuilder {

	private int kind;
	private String title;
	private int style = SWT.NONE;
	private String message = null;
	private Image specificImage;
	private String[] buttonLabels = null;
	private int defaultButtonIndex = 0;

	private MessageDialogBuilder(int kind, String title) {
		this.kind = kind;
		this.title = title;
	}

	public MessageDialogBuilder sheet() {
		style = SWT.SHEET;
		return this;
	}

	public boolean open(Shell shell) {
		return MessageDialog.open(kind, shell, this.title, this.message, this.style, this.defaultButtonIndex,
				this.buttonLabels, this.specificImage);
	}

	public MessageDialogBuilder message(String message) {
		this.message = message;
		return this;
	}

	public MessageDialogBuilder image(Image image) {
		this.specificImage = image;
		return this;
	}

	public MessageDialogBuilder buttons(String... labels) {
		this.buttonLabels = labels;
		return this;
	}

	public MessageDialogBuilder defaultButton(int defaultButtonIndex) {
		this.defaultButtonIndex = defaultButtonIndex;
		return this;
	}

	public static MessageDialogBuilder info(String title) {
		return new MessageDialogBuilder(MessageDialog.INFORMATION, title);
	}

	public static MessageDialogBuilder cancableQuestion(String title) {
		return new MessageDialogBuilder(MessageDialog.QUESTION_WITH_CANCEL, title);
	}

	public static MessageDialogBuilder confirm(String title) {
		return new MessageDialogBuilder(MessageDialog.CONFIRM, title);
	}

	public static MessageDialogBuilder question(String title) {
		return new MessageDialogBuilder(MessageDialog.QUESTION, title);
	}

	public static MessageDialogBuilder error(String title) {
		return new MessageDialogBuilder(MessageDialog.ERROR, title);
	}

	public static MessageDialogBuilder warning(String title) {
		return new MessageDialogBuilder(MessageDialog.WARNING, title);
	}
}
