package org.eclipse.ui.databinding.typed;

import java.util.Objects;
import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.CommandEvent;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.ui.handlers.RegistryToggleState;
import org.eclipse.ui.internal.databinding.CommandProperty;
import org.eclipse.ui.internal.databinding.CommandStateProperty;

public class CommandProperties {
	public static IValueProperty<Command, Boolean> enabled() {
		return new CommandProperty<Boolean>(Boolean.class) {
			@Override
			protected void doSetValue(Command source, Boolean value) {
				source.setEnabled(Boolean.TRUE.equals(value));
			}

			@Override
			protected Boolean doGetValue(Command source) {
				return source.isEnabled();
			}

			@Override
			protected boolean isChangeRelevant(CommandEvent event) {
				return event.isEnabledChanged();
			}
		};
	}

	public static IValueProperty<Command, Boolean> handled() {
		return new CommandProperty<Boolean>(Boolean.class) {
			@Override
			protected void doSetValue(Command source, Boolean value) {
				source.setEnabled(Boolean.TRUE.equals(value));
			}

			@Override
			protected Boolean doGetValue(Command source) {
				return source.isHandled();
			}

			@Override
			protected boolean isChangeRelevant(CommandEvent event) {
				return event.isHandledChanged();
			}
		};
	}

	public static IValueProperty<Command, Boolean> toggled() {
		return new CommandStateProperty<Boolean>(RegistryToggleState.STATE_ID, Boolean.class) {
			@Override
			protected void doSetValue(Command source, Boolean value) {
				source.getState(stateId).setValue(Boolean.TRUE.equals(value));
			}
		};
	}

	public static <T> IValueProperty<Command, Boolean> stateEquals(String stateId, Object stateValue) {
		return new CommandStateProperty<Boolean>(stateId, Boolean.class) {
			@Override
			protected void doSetValue(Command source, Boolean value) {
				throw new UnsupportedOperationException();
			}

			@Override
			protected Boolean doGetValue(Command source) {
				return Objects.equals(stateValue, source.getState(stateId).getValue());
			}
		};
	}

	public static <T> IValueProperty<Command, T> state(String stateId, Class<T> valueType) {
		return new CommandStateProperty<>(stateId, valueType);
	}
}
