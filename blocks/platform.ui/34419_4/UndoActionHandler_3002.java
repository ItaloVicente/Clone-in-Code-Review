package org.eclipse.ui.operations;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchCommandConstants;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.WorkbenchMessages;

public final class RedoActionHandler extends OperationHistoryActionHandler {

	public RedoActionHandler(IWorkbenchPartSite site, IUndoContext context) {
		super(site, context);
		setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
				.getImageDescriptor(ISharedImages.IMG_TOOL_REDO));
		setDisabledImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
				.getImageDescriptor(ISharedImages.IMG_TOOL_REDO_DISABLED));
		setActionDefinitionId(IWorkbenchCommandConstants.EDIT_REDO);
	}

	@Override
	void flush() {
		getHistory().dispose(getUndoContext(), false, true, false);
	}

	@Override
	String getCommandString() {
		return WorkbenchMessages.Operations_redoCommand;
	}
	
	@Override
	String getTooltipString() {
		return WorkbenchMessages.Operations_redoTooltipCommand;
	}
	
	@Override
	String getSimpleCommandString() {
		return WorkbenchMessages.Workbench_redo;
	}
	
	@Override
	String getSimpleTooltipString() {
		return WorkbenchMessages.Workbench_redoToolTip;
	}


	@Override
	IUndoableOperation getOperation() {
		return getHistory().getRedoOperation(getUndoContext());
	}

	@Override
	IStatus runCommand(IProgressMonitor pm) throws ExecutionException {
		return getHistory().redo(getUndoContext(), pm, this);
	}

	@Override
	boolean shouldBeEnabled() {
		return getHistory().canRedo(getUndoContext());
	}
}
