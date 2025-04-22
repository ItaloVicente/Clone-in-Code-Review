package org.eclipse.ui.internal.handlers;

import java.lang.reflect.Method;

import org.eclipse.core.commands.ExecutionEvent;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class TraversePageHandler extends WidgetMethodHandler {

	private static final Class[] METHOD_PARAMETERS = { int.class };

	@Override
	public final Object execute(final ExecutionEvent event) {
		Control focusControl = Display.getCurrent().getFocusControl();
		if (focusControl != null) {
			int traversal= "next".equals(methodName) ? SWT.TRAVERSE_PAGE_NEXT : SWT.TRAVERSE_PAGE_PREVIOUS; //$NON-NLS-1$
			Control control = focusControl;
			do {
				if (control.traverse (traversal))
					return null;
				if (control instanceof Shell)
					return null;
				control = control.getParent();
			} while (control != null);
		}

		return null;
	}

	@Override
	protected Method getMethodToExecute() {
		final Control focusControl = Display.getCurrent().getFocusControl();
		if (focusControl != null) {
			try {
				return focusControl.getClass().getMethod("traverse", //$NON-NLS-1$
						METHOD_PARAMETERS);
			} catch (NoSuchMethodException e) {
			}
		}
		return null;
	}

}
