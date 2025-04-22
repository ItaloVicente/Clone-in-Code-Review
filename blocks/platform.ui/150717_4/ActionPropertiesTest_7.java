package org.eclipse.ui.internal.databinding;

import java.util.Objects;
import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.IObjectWithState;
import org.eclipse.core.commands.IStateListener;
import org.eclipse.core.commands.State;
import org.eclipse.core.databinding.observable.Diffs;
import org.eclipse.core.databinding.observable.value.ValueDiff;
import org.eclipse.core.databinding.property.INativePropertyListener;
import org.eclipse.core.databinding.property.ISimplePropertyListener;
import org.eclipse.core.databinding.property.SimplePropertyEvent;
import org.eclipse.core.databinding.property.value.SimpleValueProperty;

public class StateProperty<S extends IObjectWithState, T> extends SimpleValueProperty<S, T> {
	protected final String stateId;
	private final Class<T> valueType;

	public StateProperty(String stateId, Class<T> valueType) {
		this.stateId = Objects.requireNonNull(stateId);
		this.valueType = Objects.requireNonNull(valueType);
	}

	@Override
	public Object getValueType() {
		return valueType;
	}

	@Override
	protected void doSetValue(S source, T value) {
		source.getState(stateId).setValue(value);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected T doGetValue(IObjectWithState source) {
		State state = source.getState(stateId);
		return state == null ? null : (T) state.getValue();
	}

	@Override
	public INativePropertyListener<S> adaptListener(
			ISimplePropertyListener<S, ValueDiff<? extends T>> listener) {
		class Listener implements INativePropertyListener<S>, IStateListener {
			@Override
			public void removeFrom(IObjectWithState source) {
				if (source != null) {
					source.getState(stateId).removeListener(this);
				}
			}

			@Override
			public void addTo(IObjectWithState source) {
				if (source != null) {
					source.getState(stateId).addListener(this);
				}
			}

			@Override
			public void handleStateChange(State state, Object oldValue) {
				@SuppressWarnings("unchecked")
				ValueDiff<? extends T> diff = Diffs.createValueDiff((T) oldValue, (T) state.getValue());
				listener.handleEvent(
						new SimplePropertyEvent<>(SimplePropertyEvent.CHANGE, null, StateProperty.this, diff));
			}
		}

		return new Listener();
	}
}
