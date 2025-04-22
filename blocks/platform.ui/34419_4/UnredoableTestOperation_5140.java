package org.eclipse.ui.tests.operations;

import org.eclipse.core.commands.operations.AbstractOperation;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

public class TestOperation extends AbstractOperation {

	private int fExecutionCount = 0;

	TestOperation(String label) {
		super(label);
	}

	@Override
	public boolean canRedo() {
		return fExecutionCount == 0;
	}

	@Override
	public boolean canUndo() {
		return fExecutionCount > 0;
	}

	@Override
	public IStatus execute(IProgressMonitor monitor, IAdaptable uiInfo) {
		fExecutionCount++;
		return Status.OK_STATUS;
	}

	@Override
	public IStatus redo(IProgressMonitor monitor, IAdaptable uiInfo) {
		return execute(monitor, uiInfo);
	}

	@Override
	public IStatus undo(IProgressMonitor monitor, IAdaptable uiInfo) {
		fExecutionCount--;
		return Status.OK_STATUS;
	}

	@Override
	public void dispose() {
		fExecutionCount = 0;
	}

}
