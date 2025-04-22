package org.eclipse.ui.navigator;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.DropTargetListener;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.internal.navigator.NavigatorContentService;

public abstract class CommonDropAdapterAssistant {

	private INavigatorContentService contentService;

	private DropTargetEvent _currentEvent;

	private CommonDropAdapter _dropAdapter;
	
	public final void init(INavigatorContentService aContentService) {
		contentService = aContentService;
		doInit();
	}

	
	protected void doInit() {

	}

	public abstract IStatus validateDrop(Object target, int operation,
			TransferData transferType);

	public abstract IStatus handleDrop(CommonDropAdapter aDropAdapter,
			DropTargetEvent aDropTargetEvent, Object aTarget);

	public boolean isSupportedType(TransferData aTransferType) {
		return LocalSelectionTransfer.getTransfer().isSupportedType(
				aTransferType);
	}

	public IStatus validatePluginTransferDrop(
			IStructuredSelection aDragSelection, Object aDropTarget) {
		return Status.CANCEL_STATUS;
	}

	public IStatus handlePluginTransferDrop(
			IStructuredSelection aDragSelection, Object aDropTarget) {
		return Status.CANCEL_STATUS;
	}

	protected INavigatorContentService getContentService() {
		return contentService;
	}

	protected final Shell getShell() {
		return ((NavigatorContentService) contentService).getShell();
	}

	void setCurrentEvent(DropTargetEvent event) {
		_currentEvent = event;
	}

	public DropTargetEvent getCurrentEvent() {
		return _currentEvent;
	}
	
	public void setCommonDropAdapter(CommonDropAdapter dropAdapter) {
		_dropAdapter = dropAdapter;
	}
	
    protected CommonDropAdapter getCommonDropAdapter() {
        return _dropAdapter;
    }

}
