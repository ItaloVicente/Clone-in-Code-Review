package org.eclipse.ui.internal.keys;

import org.eclipse.jface.bindings.BindingManager;
import org.eclipse.jface.bindings.Scheme;
import org.eclipse.ui.commands.IKeyConfiguration;
import org.eclipse.ui.commands.IKeyConfigurationListener;
import org.eclipse.ui.commands.NotDefinedException;

public final class SchemeLegacyWrapper implements IKeyConfiguration {

	private final BindingManager bindingManager;

	private final Scheme scheme;

	public SchemeLegacyWrapper(final Scheme scheme,
			final BindingManager bindingManager) {
		if (scheme == null) {
			throw new NullPointerException("Cannot wrap a null scheme"); //$NON-NLS-1$
		}

		if (bindingManager == null) {
			throw new NullPointerException(
					"Cannot wrap a scheme without a binding manager"); //$NON-NLS-1$
		}

		this.scheme = scheme;
		this.bindingManager = bindingManager;
	}

	@Override
	public void addKeyConfigurationListener(
			IKeyConfigurationListener keyConfigurationListener) {
		scheme.addSchemeListener(new LegacySchemeListenerWrapper(
				keyConfigurationListener, bindingManager));
	}

	@Override
	public int compareTo(Object o) {
		return scheme.compareTo(o);
	}

	@Override
	public String getDescription() throws NotDefinedException {
		try {
			return scheme.getDescription();
		} catch (final org.eclipse.core.commands.common.NotDefinedException e) {
			throw new NotDefinedException(e);
		}
	}

	@Override
	public String getId() {
		return scheme.getId();
	}

	@Override
	public String getName() throws NotDefinedException {
		try {
			return scheme.getName();
		} catch (final org.eclipse.core.commands.common.NotDefinedException e) {
			throw new NotDefinedException(e);
		}
	}

	@Override
	public String getParentId() throws NotDefinedException {
		try {
			return scheme.getParentId();
		} catch (final org.eclipse.core.commands.common.NotDefinedException e) {
			throw new NotDefinedException(e);
		}
	}

	@Override
	public boolean isActive() {
		return scheme.getId().equals(bindingManager.getActiveScheme().getId());
	}

	@Override
	public boolean isDefined() {
		return scheme.isDefined();
	}

	@Override
	public void removeKeyConfigurationListener(
			IKeyConfigurationListener keyConfigurationListener) {
		scheme.removeSchemeListener(new LegacySchemeListenerWrapper(
				keyConfigurationListener, bindingManager));

	}

}
