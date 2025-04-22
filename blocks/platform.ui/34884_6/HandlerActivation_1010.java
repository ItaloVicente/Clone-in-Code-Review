
package org.eclipse.ui.internal.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;

public abstract class ExecutableExtensionHandler extends AbstractHandler
        implements IExecutableExtension {

    @Override
	public void setInitializationData(final IConfigurationElement config,
            final String propertyName, final Object data) throws CoreException {
    }
}
