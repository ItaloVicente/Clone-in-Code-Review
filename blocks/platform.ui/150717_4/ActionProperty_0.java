package org.eclipse.jface.databinding.action;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ICommandListener;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.internal.databinding.action.ActionProperty;

public class ActionProperties {
	private ActionProperties() {
	}

	public static IValueProperty<IAction, Boolean> enabled() {
		return new ActionProperty<Boolean>(IAction.ENABLED, Boolean.class) {
			@Override
			protected void doSetValue(IAction source, Boolean value) {
				source.setEnabled(Boolean.TRUE.equals(value));
			}

			@Override
			protected Boolean doGetValue(IAction source) {
				return source.isEnabled();
			}
		};
	}

	public static IValueProperty<IAction, Boolean> checked() {
		return new ActionProperty<Boolean>(IAction.CHECKED, Boolean.class) {
			@Override
			protected void doSetValue(IAction source, Boolean value) {
				source.setChecked(Boolean.TRUE.equals(value));
			}

			@Override
			protected Boolean doGetValue(IAction source) {
				return source.isChecked();
			}
		};
	}

	public static IValueProperty<IAction, Boolean> handled() {
		return new ActionProperty<Boolean>(IAction.HANDLED, Boolean.class) {
			@Override
			protected void doSetValue(IAction source, Boolean value) {
				source.setChecked(Boolean.TRUE.equals(value));
			}

			@Override
			protected Boolean doGetValue(IAction source) {
				return source.isHandled();
			}
		};
	}

	public static IValueProperty<IAction, String> text() {
		return new ActionProperty<String>(IAction.TEXT, String.class) {
			@Override
			protected void doSetValue(IAction source, String value) {
				source.setText(value);
			}

			@Override
			protected String doGetValue(IAction source) {
				return source.getText();
			}
		};
	}

	public static IValueProperty<IAction, String> description() {
		return new ActionProperty<String>(IAction.DESCRIPTION, String.class) {
			@Override
			protected void doSetValue(IAction source, String value) {
				source.setDescription(value);
			}

			@Override
			protected String doGetValue(IAction source) {
				return source.getDescription();
			}
		};
	}
}
