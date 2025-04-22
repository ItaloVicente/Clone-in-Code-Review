
package org.eclipse.ui.swt;

import org.eclipse.swt.widgets.Control;

public interface IFocusService {
	public static final String COPY_HANDLER = "org.eclipse.ui.internal.handlers.WidgetMethodHandler:copy"; //$NON-NLS-1$

	public static final String PASTE_HANDLER = "org.eclipse.ui.internal.handlers.WidgetMethodHandler:paste"; //$NON-NLS-1$

	public static final String CUT_HANDLER = "org.eclipse.ui.internal.handlers.WidgetMethodHandler:cut"; //$NON-NLS-1$

	public static final String SELECT_ALL_HANDLER = "org.eclipse.ui.internal.handlers.SelectAllHandler"; //$NON-NLS-1$

	public void addFocusTracker(Control control, String id);

	public void removeFocusTracker(Control control);
}
