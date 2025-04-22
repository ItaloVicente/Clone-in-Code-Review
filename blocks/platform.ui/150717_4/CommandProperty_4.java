package org.eclipse.ui.internal.databinding;

import org.eclipse.core.commands.Command;

public abstract class BooleanCommandProperty extends CommandProperty<Boolean> {

	public BooleanCommandProperty() {
		super(Boolean.class);
	}

	@Override
	protected Boolean getOldValue(Boolean newValue) {
		return !Boolean.TRUE.equals(newValue);
	}
}
