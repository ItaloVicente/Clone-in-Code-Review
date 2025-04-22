package org.eclipse.ui;

public class SelectionListenerFactory {

	public static ISelectionListener getInvisiblePartSelectionListener(IWorkbenchPart part,
			ISelectionListener listener) {
		return new org.eclipse.ui.internal.InvisiblePartSelectionListener(part, listener);
	}
}
