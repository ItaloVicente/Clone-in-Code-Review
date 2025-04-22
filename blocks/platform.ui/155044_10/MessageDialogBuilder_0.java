package org.eclipse.jface.dialogs;

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

	private MessageDialogBuilder(String title) {
		this.title = title;
	}

	public MessageDialogBuilder sheet() {
		style = SWT.SHEET;
		return this;
	}

	public boolean open(Shell shell) {
		PlainMessageDialog dialog = new PlainMessageDialog(shell, title);
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

	public MessageDialogBuilder buttons(String... labels) {
		this.properties.add(d -> d.setButtonLabels(labels));
		return this;
	}

	public MessageDialogBuilder defaultButton(int defaultButtonIndex) {
		this.properties.add(d -> d.setDefaultButtonIndex(defaultButtonIndex));
		return this;
	}

	public static MessageDialogBuilder title(String title) {
		return new MessageDialogBuilder(title);
	}

	public static MessageDialogBuilder title(String title, int type) {
		return new MessageDialogBuilder(title);
	}

	public MessageDialogBuilder type(int type) {
		this.properties.add(d -> d.setImage(type));
		this.properties.add(d -> d.setButtonLabels(type));
		return this;
	}
}
