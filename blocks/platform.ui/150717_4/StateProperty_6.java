package org.eclipse.ui.internal.databinding;

import org.eclipse.core.commands.HandlerEvent;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.core.databinding.observable.Diffs;
import org.eclipse.core.databinding.observable.value.ValueDiff;
import org.eclipse.core.databinding.property.INativePropertyListener;
import org.eclipse.core.databinding.property.ISimplePropertyListener;
import org.eclipse.core.databinding.property.SimplePropertyEvent;
import org.eclipse.core.databinding.property.value.SimpleValueProperty;

public abstract class HandlerProperty extends SimpleValueProperty<IHandler, Boolean> {
	@Override
	public Object getValueType() {
		return Boolean.class;
	}

	protected abstract boolean isChangeRelevant(HandlerEvent event);

	@Override
	public INativePropertyListener<IHandler> adaptListener(
			ISimplePropertyListener<IHandler, ValueDiff<? extends Boolean>> listener) {
		class Listener implements INativePropertyListener<IHandler>, IHandlerListener {
			@Override
			public void removeFrom(IHandler source) {
				if (source != null) {
					source.removeHandlerListener(this);
				}
			}

			@Override
			public void addTo(IHandler source) {
				if (source != null) {
					source.addHandlerListener(this);
				}
			}

			@Override
			public void handlerChanged(HandlerEvent event) {
				if (isChangeRelevant(event)) {
					boolean newValue = doGetValue(event.getHandler());
					boolean oldValue = !newValue;
					listener.handleEvent(new SimplePropertyEvent<>(SimplePropertyEvent.CHANGE, event.getHandler(),
							HandlerProperty.this, Diffs.createValueDiff(oldValue, newValue)));
				}
			}

		}

		return new Listener();
	}
}
