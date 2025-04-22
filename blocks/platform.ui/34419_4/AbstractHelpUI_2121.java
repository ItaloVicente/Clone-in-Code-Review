package org.eclipse.ui.handlers;

import java.util.Map;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.descriptor.basic.MPartDescriptor;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchCommandConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.internal.dialogs.ShowViewDialog;

public final class ShowViewHandler extends AbstractHandler {


    public ShowViewHandler() {
    }

    public ShowViewHandler(boolean makeFast) {

    }

	@Override
	public final Object execute(final ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow workbenchWindow = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		Shell shell = HandlerUtil.getActiveShell(event);
		IEclipseContext ctx = workbenchWindow.getService(IEclipseContext.class);
		EModelService modelService = workbenchWindow.getService(EModelService.class);
		EPartService partService = workbenchWindow.getService(EPartService.class);
		MApplication app = workbenchWindow.getService(MApplication.class);
		MWindow window = workbenchWindow.getService(MWindow.class);

		final Map<?, ?> parameters = event.getParameters();
		final Object value = parameters.get(IWorkbenchCommandConstants.VIEWS_SHOW_VIEW_PARM_ID);

		if (value == null) {
			openOther(shell, app, window, modelService, ctx, partService);
		} else {
			try {
				openView((String) value, partService);
			} catch (PartInitException e) {
				throw new ExecutionException("Part could not be initialized", e); //$NON-NLS-1$
			}
		}

		return null;
	}

	private final void openOther(final Shell shell, MApplication app, MWindow window, EModelService modelService,
			IEclipseContext context, EPartService partService) {

		final ShowViewDialog dialog = new ShowViewDialog(shell, app, window, modelService, partService, context);
		dialog.open();

		if (dialog.getReturnCode() == Window.CANCEL) {
			return;
		}

		final MPartDescriptor[] descriptors = dialog.getSelection();
		for (MPartDescriptor descriptor : descriptors) {
			partService.showPart(descriptor.getElementId(), PartState.ACTIVATE);
		}
	}

	private final void openView(final String viewId, EPartService partService) throws PartInitException {
		partService.showPart(viewId, PartState.ACTIVATE);
	}
}
