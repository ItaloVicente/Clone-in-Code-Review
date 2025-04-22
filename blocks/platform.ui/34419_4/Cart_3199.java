package org.eclipse.jface.examples.databinding.model;

import java.util.StringTokenizer;

import org.eclipse.core.databinding.observable.Diffs;
import org.eclipse.core.databinding.observable.value.AbstractObservableValue;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.observable.value.IValueChangeListener;
import org.eclipse.core.databinding.observable.value.ValueChangeEvent;

public class AggregateObservableValue extends AbstractObservableValue {

	private IObservableValue[] observableValues;

	private String delimiter;

	private boolean updating = false;

	private String currentValue;

	private IValueChangeListener listener = new IValueChangeListener() {
		@Override
		public void handleValueChange(ValueChangeEvent event) {
			if (!updating) {
				fireValueChange(Diffs.createValueDiff(currentValue,
						doGetValue()));
			}
		}
	};

	public AggregateObservableValue(IObservableValue[] observableValues,
			String delimiter) {
		this.observableValues = observableValues;
		this.delimiter = delimiter;
		for (int i = 0; i < observableValues.length; i++) {
			observableValues[i].addValueChangeListener(listener);
		}
		doGetValue();
	}

	@Override
	public void doSetValue(Object value) {
		Object oldValue = doGetValue();
		StringTokenizer tokenizer = new StringTokenizer((String) value,
				delimiter);
		try {
			updating = true;
			for (int i = 0; i < observableValues.length; i++) {
				if (tokenizer.hasMoreElements()) {
					observableValues[i].setValue(tokenizer.nextElement());
				} else {
					observableValues[i].setValue(null);
				}
			}
		} finally {
			updating = false;
		}
		doGetValue();
		fireValueChange(Diffs.createValueDiff(oldValue, value));
	}

	@Override
	public Object doGetValue() {
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < observableValues.length; i++) {
			if (i > 0 & i < observableValues.length) {
				result.append(delimiter);
			}
			result.append(observableValues[i].getValue());
		}
		currentValue = result.toString();
		return currentValue;
	}

	@Override
	public Object getValueType() {
		return String.class;
	}

	@Override
	public synchronized void dispose() {
		for (int i = 0; i < observableValues.length; i++) {
			observableValues[i].removeValueChangeListener(listener);
		}
		super.dispose();
	}

}
