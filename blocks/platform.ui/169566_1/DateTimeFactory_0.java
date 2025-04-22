package org.eclipse.jface.widgets;

import java.util.function.Consumer;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;

public final class DateTimeFactory extends AbstractControlFactory<DateTimeFactory, DateTime> {

	private DateTimeFactory(int style) {
		super(DateTimeFactory.class, (Composite parent) -> new DateTime(parent, style));
	}

	public static DateTimeFactory newDateTime(int style) {
		return new DateTimeFactory(style);
	}

	public DateTimeFactory onSelect(Consumer<SelectionEvent> consumer) {
		addProperty(c -> c.addSelectionListener(SelectionListener.widgetSelectedAdapter(consumer)));
		return this;
	}

	public DateTimeFactory onDefaultSelect(Consumer<SelectionEvent> consumer) {
		addProperty(c -> c.addSelectionListener(SelectionListener.widgetDefaultSelectedAdapter(consumer)));
		return this;
	}
}
