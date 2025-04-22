package org.eclipse.jface.widgets;

import java.util.function.Consumer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Spinner;

public class SpinnerFactory extends AbstractCompositeFactory<SpinnerFactory, Spinner> {

	private SpinnerFactory(int style) {
		super(SpinnerFactory.class, (Composite parent) -> new Spinner(parent, style));
	}

	public static SpinnerFactory newSpinner(int style) {
		return new SpinnerFactory(style);
	}

	public SpinnerFactory bounds(int minimum, int maximum) {
		if (minimum != SWT.DEFAULT) {
			addProperty(s -> s.setMinimum(minimum));
		}
		if (maximum != SWT.DEFAULT) {
			addProperty(s -> s.setMaximum(maximum));
		}
		return this;
	}

	public SpinnerFactory increment(int increment, int pageIncrement) {
		if (increment != SWT.DEFAULT) {
			addProperty(s -> s.setIncrement(increment));
		}
		if (pageIncrement != SWT.DEFAULT) {
			addProperty(s -> s.setPageIncrement(pageIncrement));
		}
		return this;
	}

	public SpinnerFactory limitTo(int limit) {
		addProperty(s -> s.setTextLimit(limit));
		return this;
	}

	public SpinnerFactory onSelect(Consumer<SelectionEvent> consumer) {
		SelectionListener listener = SelectionListener.widgetSelectedAdapter(consumer);
		addProperty(s -> s.addSelectionListener(listener));
		return this;
	}

	public SpinnerFactory onModify(ModifyListener listener) {
		addProperty(s -> s.addModifyListener(listener));
		return this;
	}
}
