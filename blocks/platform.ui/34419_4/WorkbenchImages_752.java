
package org.eclipse.ui.internal;

import java.util.Map;
import org.eclipse.e4.core.commands.internal.HandlerServiceHandler;
import org.eclipse.e4.core.commands.internal.HandlerServiceImpl;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.ui.commands.IElementUpdater;
import org.eclipse.ui.menus.UIElement;

public class WorkbenchHandlerServiceHandler extends HandlerServiceHandler implements
		IElementUpdater {

	public WorkbenchHandlerServiceHandler(String commandId) {
		super(commandId);
	}

	@Override
	public void updateElement(UIElement element, Map parameters) {
		final IEclipseContext executionContext = getExecutionContext(null);
		if (executionContext == null) {
			return;
		}
		Object handler = HandlerServiceImpl.lookUpHandler(executionContext, commandId);
		if (handler instanceof IElementUpdater) {
			((IElementUpdater) handler).updateElement(element, parameters);
		}
	}

}
