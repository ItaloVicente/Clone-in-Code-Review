
package org.eclipse.ui.navigator;

import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.navigator.NavigatorContentService;
import org.eclipse.ui.part.PluginTransfer;

public abstract class CommonDragAdapterAssistant {

	private INavigatorContentService contentService;

	public abstract Transfer[] getSupportedTransferTypes();

	public abstract boolean setDragData(DragSourceEvent anEvent,
			IStructuredSelection aSelection);

	public void dragStart(DragSourceEvent anEvent,
			IStructuredSelection aSelection) {
	}
	
	public void dragFinished(DragSourceEvent anEvent,
			IStructuredSelection aSelection) {
	}
	
	public final void setContentService(INavigatorContentService aContentService) {
		contentService = aContentService;
	}

	public INavigatorContentService getContentService() {
		return contentService;
	}

	public final Shell getShell() {
		if (contentService != null) {
			((NavigatorContentService) contentService).getShell();
		}
		return PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
	}

}
