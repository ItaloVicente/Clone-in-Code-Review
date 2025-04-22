package org.eclipse.ui.navigator;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Item;
import org.eclipse.ui.internal.navigator.NavigatorSafeRunnable;
import org.eclipse.ui.internal.navigator.Policy;
import org.eclipse.ui.internal.navigator.dnd.NavigatorPluginDropAction;
import org.eclipse.ui.part.PluginDropAdapter;
import org.eclipse.ui.part.PluginTransfer;

public final class CommonDropAdapter extends PluginDropAdapter {

	private static final Transfer[] SUPPORTED_DROP_TRANSFERS = new Transfer[] {
			LocalSelectionTransfer.getTransfer(), FileTransfer.getInstance(),
			PluginTransfer.getInstance() };

	private final INavigatorContentService contentService;

	private final INavigatorDnDService dndService;
	
	public CommonDropAdapter(INavigatorContentService aContentService,
			StructuredViewer aStructuredViewer) {
		super(aStructuredViewer);
		contentService = aContentService;
		dndService = contentService.getDnDService();
		setFeedbackEnabled(false);
	}

	public Transfer[] getSupportedDropTransfers() {
		return SUPPORTED_DROP_TRANSFERS;
	}

	@Override
	public void dragEnter(DropTargetEvent event) {

		if (event.detail == DND.DROP_NONE)
			return;
		
		if (Policy.DEBUG_DND) {
			System.out.println("CommonDropAdapter.dragEnter: " + event); //$NON-NLS-1$
		}
		for (int i = 0; i < event.dataTypes.length; i++) {
			if (LocalSelectionTransfer.getTransfer().isSupportedType(
					event.dataTypes[i])) {
				event.currentDataType = event.dataTypes[i]; 
				if (Policy.DEBUG_DND) {
					System.out.println("CommonDropAdapter.dragEnter: local selection: " + event.currentDataType); //$NON-NLS-1$
				}
				super.dragEnter(event);
				return;
			}
		}

		for (int i = 0; i < event.dataTypes.length; i++) {
			if (FileTransfer.getInstance().isSupportedType(event.dataTypes[i])) {
				event.currentDataType = event.dataTypes[i];
				event.detail = DND.DROP_COPY; 
				if (Policy.DEBUG_DND) {
					System.out.println("CommonDropAdapter.dragEnter: file: " + event.currentDataType); //$NON-NLS-1$
				}
				super.dragEnter(event);
				return;
			}
		}

		for (int i = 0; i < event.dataTypes.length; i++) {
			if (PluginTransfer.getInstance()
					.isSupportedType(event.dataTypes[i])) {
				event.currentDataType = event.dataTypes[i]; 
				if (Policy.DEBUG_DND) {
					System.out.println("CommonDropAdapter.dragEnter: plugin: " + event.currentDataType); //$NON-NLS-1$
				}
				super.dragEnter(event);
				return;
			}
		}

		event.detail = DND.DROP_NONE; 

	}

	@Override
	public void dragLeave(DropTargetEvent event) {
		super.dragLeave(event);
		if (LocalSelectionTransfer.getTransfer().isSupportedType(
				event.currentDataType)) {
			event.data = NavigatorPluginDropAction
					.createTransferData(contentService);
		}
	}

	@Override
	public boolean performDrop(Object data) {
		final DropTargetEvent event = getCurrentEvent();
		if (Policy.DEBUG_DND) {
			System.out.println("CommonDropAdapter.drop (begin): " + event); //$NON-NLS-1$
		}
		final Object target = getCurrentTarget() != null ? 
				getCurrentTarget() : getViewer().getInput();

		validateDrop(target, getCurrentOperation(), event.currentDataType);
		if (PluginTransfer.getInstance().isSupportedType(event.currentDataType)) {
			super.drop(event);
			return true;
		}
		
		if (Policy.DEBUG_DND) {
			System.out.println("CommonDropAdapter.drop target: " + target + " op: " + getCurrentOperation()); //$NON-NLS-1$ //$NON-NLS-2$
		}
		final CommonDropAdapterAssistant[] assistants = dndService.findCommonDropAdapterAssistants(target,
				getCurrentTransfer());

		final boolean[] retValue = new boolean[1];
		for (int i = 0; i < assistants.length; i++) {
			final CommonDropAdapterAssistant localAssistant = assistants[i];
			SafeRunner.run(new NavigatorSafeRunnable() {
				@Override
				public void run() throws Exception {
					localAssistant.setCurrentEvent(event);
					IStatus valid = localAssistant.validateDrop(target, getCurrentOperation(),
							getCurrentTransfer());
					if (valid != null && valid.isOK()) {
						if (Policy.DEBUG_DND) {
							System.out
									.println("CommonDropAdapter.drop assistant selected: " + localAssistant + " op: " + event.detail); //$NON-NLS-1$ //$NON-NLS-2$
						}
						localAssistant.handleDrop(CommonDropAdapter.this, event, target);
						retValue[0] = true;
					}
				}
			});
			if (retValue[0])
				return true;
		}

		return false;
	}

	@Override
	public boolean validateDrop(final Object aDropTarget, final int theDropOperation,
			final TransferData theTransferData) {

		if (Policy.DEBUG_DND) {
			System.out.println("CommonDropAdapter.validateDrop (begin) operation: " + theDropOperation + " target: " + aDropTarget /*+ " transferType: " + theTransferData.type*/); //$NON-NLS-1$ //$NON-NLS-2$
		}

		boolean result = false;
		final IStatus[] valid = new IStatus[1];

		if (super.validateDrop(aDropTarget, theDropOperation, theTransferData)) {
			result = true;
			if (Policy.DEBUG_DND) {
				System.out.println("CommonDropAdapter.validateDrop valid for plugin transfer"); //$NON-NLS-1$
			}
		} else {
			final Object target = aDropTarget != null ? aDropTarget : getViewer().getInput();
			if (Policy.DEBUG_DND) {
				System.out.println("CommonDropAdapter.validateDrop target: " + target); //$NON-NLS-1$
				System.out.println("CommonDropAdapter.validateDrop local selection: " + //$NON-NLS-1$
						LocalSelectionTransfer.getTransfer().getSelection());
			}
			CommonDropAdapterAssistant[] assistants = dndService.findCommonDropAdapterAssistants(
					target, theTransferData);
			for (int i = 0; i < assistants.length; i++) {
				if (Policy.DEBUG_DND) {
					System.out
							.println("CommonDropAdapter.validateDrop checking assistant: \"" + assistants[i]); //$NON-NLS-1$
				}
				final CommonDropAdapterAssistant assistantLocal = assistants[i];

				SafeRunner.run(new NavigatorSafeRunnable() {
					@Override
					public void run() throws Exception {
						assistantLocal.setCurrentEvent(getCurrentEvent());
						valid[0] = assistantLocal.validateDrop(target, theDropOperation,
								theTransferData);
					}
				});
				if (valid[0] != null && valid[0].isOK()) {
					result = true;
					if (Policy.DEBUG_DND) {
						System.out.println("CommonDropAdapter.validateDrop VALID"); //$NON-NLS-1$
					}
					break;
				}
				if (Policy.DEBUG_DND) {
					System.out
							.println("CommonDropAdapter.validateDrop NOT valid: " + (valid[0] != null ? (valid[0].getSeverity() + ": " + valid[0].getMessage()) : "")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ 
				}
			}
		}

		if (Policy.DEBUG_DND) {
			System.out
					.println("CommonDropAdapter.validateDrop (returning " + (valid[0] != null ? valid[0].getSeverity() + ": " + valid[0].getMessage() : "" + result) + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		}

		setScrollExpandEnabled(true);
		return result;

	}


	@Override
	public Rectangle getBounds(Item item) {
		return super.getBounds(item);
	}

	@Override
	public int getCurrentLocation() {
		return super.getCurrentLocation();
	}

	@Override
	public int getCurrentOperation() {
		return super.getCurrentOperation();
	}

	@Override
	public void overrideOperation(int operation) {
		if (Policy.DEBUG_DND) {
			System.out.println("CommonDropAdapter.overrideOperation: " + operation); //$NON-NLS-1$
		}
		super.overrideOperation(operation);
	}

	@Override
	public Object getCurrentTarget() {
		return super.getCurrentTarget();
	}

	@Override
	public TransferData getCurrentTransfer() {
		return super.getCurrentTransfer();
	}
	

}
