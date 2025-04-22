package org.eclipse.jface.widgets;

import java.util.function.Consumer;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Sash;

public class SashFactory extends AbstractControlFactory<SashFactory, Sash> {

	private SashFactory(int style) {
		super(SashFactory.class, (composite) -> new Sash(composite, style));
	}

	public static SashFactory newSash(int style) {
		return new SashFactory(style);
	}

	public SashFactory onSelect(Consumer<SelectionEvent> consumer) {
		addProperty(c -> c.addSelectionListener(SelectionListener.widgetSelectedAdapter(consumer)));
		return this;
	}
}
