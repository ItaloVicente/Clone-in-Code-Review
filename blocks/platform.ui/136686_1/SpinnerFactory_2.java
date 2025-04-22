package org.eclipse.jface.widgets;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Spinner;

public class SpinnerFactory extends AbstractCompositeFactory<SpinnerFactory, Spinner> {

	private int minimum = SWT.DEFAULT;
	private int maximum = SWT.DEFAULT;
	private int increment = SWT.DEFAULT;
	private int pageIncrement = SWT.DEFAULT;
	private int limit = SWT.DEFAULT;
	private Collection<SelectionListener> selectionListeners = new ArrayList<>();
	private Collection<ModifyListener> modifyListeners = new ArrayList<>();

	private SpinnerFactory(int style) {
		super(SpinnerFactory.class, (Composite parent) -> new Spinner(parent, style));
	}

	public static SpinnerFactory createSpinner(int style) {
		return new SpinnerFactory(style);
	}

	public SpinnerFactory bounds(int minimum, int maximum) {
		this.minimum = minimum;
		this.maximum = maximum;
		return this;
	}

	public SpinnerFactory increment(int increment, int pageIncrement) {
		this.increment = increment;
		this.pageIncrement = pageIncrement;
		return this;
	}

	public SpinnerFactory limitTo(int limit) {
		this.limit = limit;
		return this;
	}

	public SpinnerFactory onSelect(Consumer<SelectionEvent> consumer) {
		this.selectionListeners.add(SelectionListener.widgetSelectedAdapter(consumer));
		return this;
	}

	public SpinnerFactory onModify(ModifyListener listener) {
		this.modifyListeners.add(listener);
		return this;
	}

	@Override
	protected void applyProperties(Spinner spinner) {
		super.applyProperties(spinner);

		if (minimum != SWT.DEFAULT) {
			spinner.setMinimum(minimum);
		}
		if (maximum != SWT.DEFAULT) {
			spinner.setMaximum(maximum);
		}
		if (increment > SWT.DEFAULT) {
			spinner.setIncrement(increment);
		}
		if (pageIncrement > SWT.DEFAULT) {
			spinner.setPageIncrement(pageIncrement);
		}
		if (limit > SWT.DEFAULT) {
			spinner.setTextLimit(limit);
		}
		this.selectionListeners.forEach(l -> spinner.addSelectionListener(l));
		this.modifyListeners.forEach(l -> spinner.addModifyListener(l));
	}
}
