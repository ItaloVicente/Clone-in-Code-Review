package org.eclipse.egit.ui.internal.components;

import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;

public class EditorVisibilityTracker extends PartVisibilityListener {

	private Runnable runnable;

	public EditorVisibilityTracker(IWorkbenchPart part) {
		super(part);
	}

	public void runWhenVisible(Runnable code) {
		if (isVisible()) {
			code.run();
		} else {
			runnable = code;
		}
	}

	@Override
	protected void setVisible(boolean visible) {
		super.setVisible(visible);
		if (visible && runnable != null) {
			try {
				runnable.run();
			} finally {
				runnable = null;
			}
		}
	}

	@Override
	public void partActivated(IWorkbenchPartReference partRef) {
	}
}
