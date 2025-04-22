package org.eclipse.ui.internal.keys;

import org.eclipse.jface.bindings.BindingManager;
import org.eclipse.jface.bindings.ISchemeListener;
import org.eclipse.jface.bindings.SchemeEvent;
import org.eclipse.ui.commands.IKeyConfiguration;
import org.eclipse.ui.commands.IKeyConfigurationListener;
import org.eclipse.ui.commands.KeyConfigurationEvent;

final class LegacySchemeListenerWrapper implements ISchemeListener {

	private final BindingManager bindingManager;

	private final IKeyConfigurationListener listener;

	LegacySchemeListenerWrapper(final IKeyConfigurationListener listener,
			final BindingManager bindingManager) {
		if (listener == null) {
			throw new NullPointerException("Cannot wrap a null listener"); //$NON-NLS-1$
		}

		if (bindingManager == null) {
			throw new NullPointerException(
					"Cannot wrap a listener without a binding manager"); //$NON-NLS-1$
		}

		this.listener = listener;
		this.bindingManager = bindingManager;
	}

	@Override
	public final boolean equals(final Object object) {
		if (object instanceof LegacySchemeListenerWrapper) {
			final LegacySchemeListenerWrapper wrapper = (LegacySchemeListenerWrapper) object;
			return listener.equals(wrapper.listener);
		}

		if (object instanceof IKeyConfigurationListener) {
			final IKeyConfigurationListener other = (IKeyConfigurationListener) object;
			return listener.equals(other);
		}

		return false;
	}

	@Override
	public final int hashCode() {
		return listener.hashCode();
	}

	@Override
	public final void schemeChanged(final SchemeEvent schemeEvent) {
		final IKeyConfiguration keyConfiguration = new SchemeLegacyWrapper(
				schemeEvent.getScheme(), bindingManager);
		final boolean definedChanged = schemeEvent.isDefinedChanged();
		final boolean nameChanged = schemeEvent.isNameChanged();
		final boolean parentIdChanged = schemeEvent.isParentIdChanged();

		listener.keyConfigurationChanged(new KeyConfigurationEvent(
				keyConfiguration, false, definedChanged, nameChanged,
				parentIdChanged));
	}
}
