package org.eclipse.jface.widgets;

import java.util.function.Consumer;

import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

public class TextFactory extends AbstractControlFactory<TextFactory, Text> {

	private TextFactory(int style) {
		super(TextFactory.class, (Composite parent) -> new Text(parent, style));
	}

	public static TextFactory newText(int style) {
		return new TextFactory(style);
	}

	public TextFactory text(String text) {
		addProperty(t -> t.setText(text));
		return this;
	}

	public TextFactory message(String message) {
		addProperty(t -> t.setMessage(message));
		return this;
	}

	public TextFactory limitTo(int limit) {
		addProperty(t -> t.setTextLimit(limit));
		return this;
	}

	public TextFactory onSelect(Consumer<SelectionEvent> consumer) {
		addProperty(t -> t.addSelectionListener(SelectionListener.widgetSelectedAdapter(consumer)));
		return this;
	}

	public TextFactory onModify(ModifyListener listener) {
		addProperty(t -> t.addModifyListener(listener));
		return this;
	}

	public TextFactory onVerify(VerifyListener listener) {
		addProperty(l -> l.addVerifyListener(listener));
		return this;
	}
}
