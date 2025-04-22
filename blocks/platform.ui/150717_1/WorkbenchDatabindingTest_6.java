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

public abstract class HandlerProperty<T> extends SimpleValueProperty<IHandler, T> {
	private final Class<T> valueType;

	public HandlerProperty(Class<T> valueType) {
		this.valueType = valueType;
	}

	@Override
	public Object getValueType() {
		return valueType;
	}

	protected abstract boolean isChangeRelevant(HandlerEvent event);

	@Override
	public INativePropertyListener<IHandler> adaptListener(
			ISimplePropertyListener<IHandler, ValueDiff<? extends T>> listener) {
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
					listener.handleEvent(new SimplePropertyEvent<>(SimplePropertyEvent.CHANGE, event.getHandler(),
							HandlerProperty.this, Diffs.createValueDiff(null, doGetValue(event.getHandler()))));
				}
			}

		}

		return new Listener();
	}
}
