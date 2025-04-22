package org.eclipse.jface.dialogs;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;

public class MessageDialogBuilder {
	private List<Consumer<PlainMessageDialog>> properties = new LinkedList<>();
	private String title;
	private int style = SWT.NONE;
	private int type;

	private MessageDialogBuilder(String title, int type) {
		this.title = title;
		this.type = type;
	}

	public static MessageDialogBuilder title(String title) {
		return new MessageDialogBuilder(title, SWT.NONE);
	}

	public static MessageDialogBuilder error(String title) {
		return new MessageDialogBuilder(title, SWT.ERROR);
	}

	public MessageDialogBuilder sheet() {
		style = SWT.SHEET;
		return this;
	}
	public boolean open(Shell shell) {
		PlainMessageDialog dialog = new PlainMessageDialog(shell, title);
		if (type == SWT.ERROR) {
			dialog.setImage(dialog.getErrorImage());
			dialog.setButtonLabels(Collections.singletonList(IDialogConstants.OK_LABEL));
		}
		this.properties.forEach(p -> p.accept(dialog));
		return dialog.open(style) == 0;
	}
	public MessageDialogBuilder message(String message) {
		this.properties.add(d -> d.setMessage(message));
		return this;
	}
	public MessageDialogBuilder image(Image image) {
		this.properties.add(d -> d.setImage(image));
		return this;
	}
	public MessageDialogBuilder buttonLabels(List<String> labels) {
		this.properties.add(d -> d.setButtonLabels(labels));
		return this;
	}
	public MessageDialogBuilder defaultButton(int defaultButtonIndex) {
		this.properties.add(d -> d.setDefaultButtonIndex(defaultButtonIndex));
		return this;
	}
}
