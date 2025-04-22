package org.eclipse.jface.databinding.action;

import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.internal.databinding.action.ActionProperty;

public class ActionProperties {
	public static IValueProperty<IAction, Boolean> enabled() {
		return new ActionProperty(IAction.ENABLED) {
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
		return new ActionProperty(IAction.CHECKED) {
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
		return new ActionProperty(IAction.HANDLED) {
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
}
