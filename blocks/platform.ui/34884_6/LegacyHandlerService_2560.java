
package org.eclipse.ui.internal.handlers;

import java.util.Collections;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.commands.AbstractHandler;
import org.eclipse.ui.commands.ExecutionException;
import org.eclipse.ui.commands.IHandler;
import org.eclipse.ui.internal.WorkbenchPlugin;

public final class LegacyHandlerProxy extends AbstractHandler {

	private static final String HANDLER_ATTRIBUTE_NAME = "handler"; //$NON-NLS-1$

	private IConfigurationElement configurationElement;

	private IHandler handler;

	public LegacyHandlerProxy(
			final IConfigurationElement newConfigurationElement) {
		configurationElement = newConfigurationElement;
		handler = null;
	}

	@Override
	public void dispose() {
		if (handler != null) {
			handler.dispose();
		}
	}

	@Override
	public Object execute(Map parameters) throws ExecutionException {
		if (loadHandler()) {
			return handler.execute(parameters);
		}

		return null;
	}

	@Override
	public Map getAttributeValuesByName() {
		if (loadHandler()) {
			return handler.getAttributeValuesByName();
		} else {
			return Collections.EMPTY_MAP;
		}
	}

	private final boolean loadHandler() {
		if (handler == null) {
			try {
				handler = (IHandler) configurationElement
						.createExecutableExtension(HANDLER_ATTRIBUTE_NAME);
				configurationElement = null;
				return true;
			} catch (final CoreException e) {
				final String message = "The proxied handler for '" + configurationElement.getAttribute(HANDLER_ATTRIBUTE_NAME) //$NON-NLS-1$
						+ "' could not be loaded"; //$NON-NLS-1$
				IStatus status = new Status(IStatus.ERROR,
						WorkbenchPlugin.PI_WORKBENCH, 0, message, e);
				WorkbenchPlugin.log(message, status);
				return false;
			}
		}

		return true;
	}

	@Override
	public final String toString() {
		final StringBuffer buffer = new StringBuffer();

		buffer.append("LegacyProxy("); //$NON-NLS-1$
		if (handler == null) {
			final String className = configurationElement
					.getAttribute(HANDLER_ATTRIBUTE_NAME);
			buffer.append(className);
		} else {
			buffer.append(handler);
		}
		buffer.append(')');

		return buffer.toString();
	}
}
