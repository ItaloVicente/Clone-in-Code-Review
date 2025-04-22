
package org.eclipse.e4.core.commands.internal;

import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.e4.core.contexts.IEclipseContext;

public interface ICommandHelpService {

	public String getHelpContextId(String commandId, IEclipseContext context)
			throws NotDefinedException;

	public void setHelpContextId(IHandler handler, String contextId);
}
