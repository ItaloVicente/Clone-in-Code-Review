package org.eclipse.e4.ui.internal.workbench.swt;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.internal.contexts.EclipseContext;
import org.eclipse.e4.core.internal.contexts.IEclipseContextDebugger;

public class ContextListener implements IEclipseContextDebugger {

	@Override
	public void notify(EclipseContext context, EventType type, Object data) {
		if (!Policy.DEBUG_CONTEXTS) {
			return;
		}
		String msg = context.toString();
		if (msg.startsWith("Anonymous Context")) {
			return;
		}
		msg = type + " : " + describe(context);
		WorkbenchSWTActivator.trace(Policy.DEBUG_CONTEXTS_FLAG, msg, null);
	}

	private String describe(IEclipseContext context) {
		StringBuilder sb = new StringBuilder(); // $NON-NLS-1$
		IEclipseContext activeContext = context;
		IEclipseContext parent = context.getParent();
		while (parent != null) {
			sb.append(activeContext).append(" <- "); //$NON-NLS-1$
			activeContext = parent;
			parent = parent.getParent();
		}
		sb.append(activeContext);
		return sb.toString();
	}

}
