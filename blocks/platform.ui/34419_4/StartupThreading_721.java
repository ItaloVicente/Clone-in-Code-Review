
package org.eclipse.ui.internal;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.model.application.ui.menu.MToolControl;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.progress.ProgressRegion;
import org.eclipse.ui.internal.util.PrefUtil;

public class StandardTrim {
	@Inject
	EModelService modelService;

	private StatusLineManager manager;

	@PostConstruct
	void createWidget(Composite parent, MToolControl toolControl) {
		if (toolControl.getElementId().equals("org.eclipse.ui.StatusLine")) { //$NON-NLS-1$
			createStatusLine(parent, toolControl);
		} else if (toolControl.getElementId().equals("org.eclipse.ui.HeapStatus")) { //$NON-NLS-1$
			createHeapStatus(parent, toolControl);
		} else if (toolControl.getElementId().equals("org.eclipse.ui.ProgressBar")) { //$NON-NLS-1$
			createProgressBar(parent, toolControl);
		}
	}

	@PreDestroy
	void destroy() {
		if (manager != null) {
			manager.dispose();
			manager = null;
		}
	}

	private void createProgressBar(Composite parent, MToolControl toolControl) {
		ProgressRegion progressRegion = new ProgressRegion();
		IEclipseContext context = modelService.getContainingContext(toolControl);
		WorkbenchWindow wbw = (WorkbenchWindow) context.get(IWorkbenchWindow.class);
		progressRegion.createContents(parent, wbw);
	}

	private void createHeapStatus(Composite parent, MToolControl toolControl) {
		new HeapStatus(parent, PrefUtil.getInternalPreferenceStore());
	}

	private void createStatusLine(Composite parent, MToolControl toolControl) {
		IEclipseContext context = modelService.getContainingContext(toolControl);
		WorkbenchWindow wbw = (WorkbenchWindow) context.get(IWorkbenchWindow.class);
		if (wbw == null) {
			Workbench wb = (Workbench) PlatformUI.getWorkbench();
			wb.createWorkbenchWindow(wb.getDefaultPageInput(), null,
					modelService.getTopLevelWindowFor(toolControl), false);
			wbw = (WorkbenchWindow) context.get(IWorkbenchWindow.class);
		}

		if (wbw != null) {
			Workbench wb = (Workbench) PlatformUI.getWorkbench();
			wb.createWorkbenchWindow(wb.getDefaultPageInput(), null,
					modelService.getTopLevelWindowFor(toolControl), false);
			wbw = (WorkbenchWindow) context.get(IWorkbenchWindow.class);

			manager = wbw.getStatusLineManager();
			manager.createControl(parent);
		}
	}
}
