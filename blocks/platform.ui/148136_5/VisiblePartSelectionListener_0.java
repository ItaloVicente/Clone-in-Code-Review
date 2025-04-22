package org.eclipse.ui;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.ui.internal.VisiblePartSelectionListener;
import org.eclipse.ui.internal.VisiblePartSelectionListener2;

public class SelectionListenerFactory {

	public static ISelectionListener getVisiblePartSelectionListener(IWorkbenchPart part, ISelectionListener listener) {
		return new VisiblePartSelectionListener(part, listener);
	}

	public static ISelectionListener getVisiblePartSelectionListener2(IWorkbenchPart part,
			ISelectionListener listener) {
		return new VisiblePartSelectionListener2(part, listener);
	}
}
