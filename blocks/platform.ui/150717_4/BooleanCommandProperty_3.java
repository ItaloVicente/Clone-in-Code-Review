package org.eclipse.ui.databinding.typed;

import org.eclipse.core.commands.HandlerEvent;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.ui.internal.databinding.HandlerProperty;

public class HandlerProperties {
	private HandlerProperties() {
	}

	public static IValueProperty<IHandler, Boolean> enabled() {
		return new HandlerProperty() {
			@Override
			protected void doSetValue(IHandler source, Boolean value) {
				throw new UnsupportedOperationException();

			}

			@Override
			protected Boolean doGetValue(IHandler source) {
				return source.isEnabled();
			}

			@Override
			protected boolean isChangeRelevant(HandlerEvent event) {
				return event.isEnabledChanged();
			}
		};
	}

	public static IValueProperty<IHandler, Boolean> handled() {
		return new HandlerProperty() {
			@Override
			protected void doSetValue(IHandler source, Boolean value) {
				throw new UnsupportedOperationException();
			}

			@Override
			protected Boolean doGetValue(IHandler source) {
				return source.isHandled();
			}

			@Override
			protected boolean isChangeRelevant(HandlerEvent event) {
				return event.isHandledChanged();
			}
		};
	}
}
