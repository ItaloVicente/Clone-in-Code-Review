package org.eclipse.ui.internal.databinding;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.CommandEvent;
import org.eclipse.core.commands.ICommandListener;
import org.eclipse.core.databinding.observable.Diffs;
import org.eclipse.core.databinding.observable.value.ValueDiff;
import org.eclipse.core.databinding.property.INativePropertyListener;
import org.eclipse.core.databinding.property.ISimplePropertyListener;
import org.eclipse.core.databinding.property.SimplePropertyEvent;
import org.eclipse.core.databinding.property.value.SimpleValueProperty;

public abstract class CommandProperty<T> extends SimpleValueProperty<Command, T> {
	private final Class<T> valueType;

	public CommandProperty(Class<T> valueType) {
		this.valueType = valueType;
	}

	@Override
	public Object getValueType() {
		return valueType;
	}

	protected abstract boolean isChangeRelevant(CommandEvent event);

	@Override
	public INativePropertyListener<Command> adaptListener(
			ISimplePropertyListener<Command, ValueDiff<? extends T>> listener) {
		class Listener implements INativePropertyListener<Command>, ICommandListener {
			@Override
			public void removeFrom(Command source) {
				if (source != null) {
					source.removeCommandListener(this);
				}
			}

			@Override
			public void addTo(Command source) {
				if (source != null) {
					source.addCommandListener(this);
				}
			}

			@Override
			public void commandChanged(CommandEvent event) {
				if (isChangeRelevant(event)) {
					T newValue = doGetValue(event.getCommand());
					T oldValue = getOldValue(newValue);
					listener.handleEvent(new SimplePropertyEvent<>(SimplePropertyEvent.CHANGE, event.getCommand(),
							CommandProperty.this, Diffs.createValueDiff(oldValue, newValue)));
				}
			}

		}

		return new Listener();
	}

	protected T getOldValue(@SuppressWarnings("unused") T newValue) {
		return null;
	}
}
