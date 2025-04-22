package org.eclipse.ui.internal.databinding;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.IStateListener;
import org.eclipse.core.commands.State;
import org.eclipse.core.databinding.observable.Diffs;
import org.eclipse.core.databinding.observable.value.ValueDiff;
import org.eclipse.core.databinding.property.INativePropertyListener;
import org.eclipse.core.databinding.property.ISimplePropertyListener;
import org.eclipse.core.databinding.property.SimplePropertyEvent;
import org.eclipse.core.databinding.property.value.SimpleValueProperty;

public class CommandStateProperty<T> extends SimpleValueProperty<Command, T> {
	protected final String stateId;
	private final Class<T> valueType;

	public CommandStateProperty(String stateId, Class<T> valueType) {
		this.stateId = stateId;
		this.valueType = valueType;
	}

	@Override
	public Object getValueType() {
		return valueType;
	}

	@Override
	protected void doSetValue(Command source, T value) {
		source.getState(stateId).setValue(value);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected T doGetValue(Command source) {
		return (T) source.getState(stateId).getValue();
	}

	@Override
	public INativePropertyListener<Command> adaptListener(
			ISimplePropertyListener<Command, ValueDiff<? extends T>> listener) {
		class Listener implements INativePropertyListener<Command>, IStateListener {
			@Override
			public void removeFrom(Command source) {
				if (source != null) {
					source.getState(stateId).removeListener(this);
				}
			}

			@Override
			public void addTo(Command source) {
				if (source != null) {
					source.getState(stateId).addListener(this);
				}
			}

			@Override
			public void handleStateChange(State state, Object oldValue) {
				@SuppressWarnings("unchecked")
				ValueDiff<? extends T> diff = Diffs.createValueDiff((T) oldValue, (T) state.getValue());
				listener.handleEvent(
						new SimplePropertyEvent<>(SimplePropertyEvent.CHANGE, null, CommandStateProperty.this, diff));
			}
		}

		return new Listener();
	}
}
