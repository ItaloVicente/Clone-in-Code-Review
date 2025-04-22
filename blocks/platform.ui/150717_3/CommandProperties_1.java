package org.eclipse.jface.internal.databinding.action;

import org.eclipse.core.databinding.observable.Diffs;
import org.eclipse.core.databinding.observable.value.ValueDiff;
import org.eclipse.core.databinding.property.INativePropertyListener;
import org.eclipse.core.databinding.property.ISimplePropertyListener;
import org.eclipse.core.databinding.property.SimplePropertyEvent;
import org.eclipse.core.databinding.property.value.SimpleValueProperty;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;

public abstract class ActionProperty extends SimpleValueProperty<IAction, Boolean> {
	private final String actionProperty;

	public ActionProperty(String actionProperty) {
		this.actionProperty = actionProperty;
	}

	@Override
	public Object getValueType() {
		return Boolean.class;
	}

	@Override
	public INativePropertyListener<IAction> adaptListener(
			ISimplePropertyListener<IAction, ValueDiff<? extends Boolean>> listener) {
		class Listener implements INativePropertyListener<IAction>, IPropertyChangeListener {
			@Override
			public void removeFrom(IAction source) {
				if (source != null) {
					source.removePropertyChangeListener(this);
				}
			}

			@Override
			public void addTo(IAction source) {
				if (source != null) {
					source.addPropertyChangeListener(this);
				}
			}

			@Override
			public void propertyChange(PropertyChangeEvent event) {
				if (actionProperty.equals(event.getProperty())) {
					ValueDiff<Boolean> diff = Diffs.createValueDiff((Boolean) event.getOldValue(),
							(Boolean) event.getNewValue());
					listener.handleEvent(new SimplePropertyEvent<>(SimplePropertyEvent.CHANGE,
							(IAction) event.getSource(), ActionProperty.this, diff));
				}
			}
		}

		return new Listener();
	}
}
