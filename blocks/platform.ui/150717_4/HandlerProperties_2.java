package org.eclipse.ui.databinding.typed;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.CommandEvent;
import org.eclipse.core.commands.ICommandListener;
import org.eclipse.core.commands.IObjectWithState;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.core.databinding.observable.value.ValueDiff;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.ui.handlers.RegistryToggleState;
import org.eclipse.ui.internal.databinding.BooleanCommandProperty;
import org.eclipse.ui.internal.databinding.CommandProperty;
import org.eclipse.ui.internal.databinding.StateProperty;

public class CommandProperties {
	private CommandProperties() {
	}

	public static IValueProperty<Command, Boolean> enabled() {
		return new BooleanCommandProperty() {
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
		return new BooleanCommandProperty() {
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
				return event.isNameChanged();
			}
		};
	}

	public static <S extends IObjectWithState> IValueProperty<S, Boolean> toggled() {
		return new StateProperty<S, Boolean>(RegistryToggleState.STATE_ID, Boolean.class) {
			@Override
			protected void doSetValue(IObjectWithState source, Boolean value) {
				source.getState(stateId).setValue(Boolean.TRUE.equals(value));
			}
		};
	}

	public static <T> IValueProperty<IObjectWithState, T> state(String stateId, Class<T> valueType) {
		return new StateProperty<>(stateId, valueType);
	}

	public static <T> IValueProperty<Command, String> name() {
		return new CommandProperty<String>(String.class) {
			@Override
			protected boolean isChangeRelevant(CommandEvent event) {
				return event.isNameChanged();
			}

			@Override
			protected String doGetValue(Command source) {
				try {
					return source.getName();
				} catch (NotDefinedException e) {
					return null;
				}
			}

			@Override
			protected void doSetValue(Command source, String value) {
				throw new UnsupportedOperationException();
			}
		};
	}

	public static <T> IValueProperty<Command, String> description() {
		return new CommandProperty<String>(String.class) {
			@Override
			protected boolean isChangeRelevant(CommandEvent event) {
				return event.isDescriptionChanged();
			}

			@Override
			protected String doGetValue(Command source) {
				try {
					return source.getDescription();
				} catch (NotDefinedException e) {
					return null;
				}
			}

			@Override
			protected void doSetValue(Command source, String value) {
				throw new UnsupportedOperationException();
			}
		};
	}
}
