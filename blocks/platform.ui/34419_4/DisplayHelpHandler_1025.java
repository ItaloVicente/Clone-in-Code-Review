
package org.eclipse.ui.internal.handlers;

import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.ISaveablePart;
import org.eclipse.ui.ISources;
import org.eclipse.ui.IWindowListener;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.internal.Workbench;
import org.eclipse.ui.services.IEvaluationService;

public class DirtyStateTracker implements IPartListener, IWindowListener,
		IPropertyListener {

	private final IWorkbench workbench;

	public DirtyStateTracker() {

		workbench = Workbench.getInstance();
		workbench.addWindowListener(this);
		IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
		register(window);
	}

	public void update() {
		IEvaluationService service = workbench
				.getService(IEvaluationService.class);
		service.requestEvaluation(ISources.ACTIVE_PART_NAME);
	}

	private void register(IWorkbenchWindow window) {
		if (window == null)
			return;
		window.getPartService().addPartListener(this);
	}

	@Override
	public void partActivated(IWorkbenchPart part) {
		if (part instanceof ISaveablePart) {
			part.addPropertyListener(this);
		}
	}

	@Override
	public void partBroughtToTop(IWorkbenchPart part) {
	}

	@Override
	public void partClosed(IWorkbenchPart part) {
		if (part instanceof ISaveablePart) {
			part.removePropertyListener(this);
			update();
		}
	}

	@Override
	public void partDeactivated(IWorkbenchPart part) {
	}

	@Override
	public void partOpened(IWorkbenchPart part) {
		if (part instanceof ISaveablePart) {
			part.addPropertyListener(this);
		}
	}

	@Override
	public void windowActivated(IWorkbenchWindow window) {
		register(window);
	}

	@Override
	public void windowDeactivated(IWorkbenchWindow window) {
	}

	@Override
	public void windowClosed(IWorkbenchWindow window) {
		window.getPartService().removePartListener(this);
	}

	@Override
	public void windowOpened(IWorkbenchWindow window) {
		register(window);
	}

	@Override
	public void propertyChanged(Object source, int propID) {
		if (source instanceof ISaveablePart && propID == ISaveablePart.PROP_DIRTY) {
			update();
		}
	}

}
