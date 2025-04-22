package org.eclipse.jface.widgets;

import java.util.function.Consumer;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

public final class ComboFactory extends AbstractControlFactory<ComboFactory, Combo> {

	private ComboFactory(int style) {
		super(ComboFactory.class, (Composite parent) -> new Combo(parent, style));
	}

	public static ComboFactory newCombo(int style) {
		return new ComboFactory(style);
	}

	public ComboFactory text(String text) {
		addProperty(l -> l.setText(text));
		return this;
	}

	public ComboFactory onSelect(Consumer<SelectionEvent> consumer) {
		addProperty(c -> c.addSelectionListener(SelectionListener.widgetSelectedAdapter(consumer)));
		return this;
	}

	public ComboFactory items(String...items) {
		addProperty(c -> c.setItems(items));
		return this;
	}


}
