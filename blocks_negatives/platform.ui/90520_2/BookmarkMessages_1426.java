/*******************************************************************************
 * Copyright (c) 2006, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 ******************************************************************************/

package org.eclipse.ui.internal.ide.undo;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.IAdvancedUndoableOperation;
import org.eclipse.core.commands.operations.IAdvancedUndoableOperation2;
import org.eclipse.core.commands.operations.IOperationHistory;
import org.eclipse.core.commands.operations.IOperationHistoryListener;
import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.core.commands.operations.OperationHistoryEvent;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.undo.WorkspaceUndoUtil;
import org.eclipse.ui.internal.ide.Policy;

/**
 * WorkspaceUndoMonitor monitors the workspace for resource changes and
 * periodically checks the undo history to make sure it is valid.
 *
 * This class is not intended to be instantiated or used by clients.
 *
 * @since 3.3
 *
 */
public class WorkspaceUndoMonitor {

	/**
	 * Singleton instance.
	 */
	private static WorkspaceUndoMonitor instance;

	/**
	 * Number of workspace changes that will cause validation of undo history
	 */
	private static int CHANGE_THRESHHOLD = 10;

	/**
	 * Prefix to use on debug info
	 */

	/**
	 * Get the singleton instance of this class.
	 *
	 * @return the singleton instance of this class.
	 */
	public static WorkspaceUndoMonitor getInstance() {
		if (instance == null) {
			instance = new WorkspaceUndoMonitor();
		}
		return instance;
	}

	/**
	 * Number of workspace changes that have occurred since the last undoable
	 * operation was executed, undone, or redone.
	 */
	private int numChanges = 0;

	/**
	 * The IUndoableOperation in progress, or <code>null</code> if there is
	 * none in progress.
	 */
	private IUndoableOperation operationInProgress = null;

	/**
	 * Resource listener used to determine how often to validate the workspace
	 * undo history.
	 */
	private IResourceChangeListener resourceListener;

	/**
	 * Operation history listener used to determine whether there is an undoable
	 * operation in progress.
	 */
	private IOperationHistoryListener historyListener;

	/**
	 * Construct an instance. Should only be called by {@link #getInstance()}
	 */
	private WorkspaceUndoMonitor() {
		if (Policy.DEBUG_UNDOMONITOR) {
		}
		resourceListener = getResourceChangeListener();
		ResourcesPlugin.getWorkspace().addResourceChangeListener(
				resourceListener);

		historyListener = getOperationHistoryListener();
		getOperationHistory().addOperationHistoryListener(historyListener);

	}

	/**
	 * Get a change listener for listening to resource changes.
	 *
	 * @return the resource change listeners
	 */
	private IResourceChangeListener getResourceChangeListener() {
		return event -> {
			if (operationInProgress != null) {
				return;
			}
			if (event.getType() == IResourceChangeEvent.POST_CHANGE
					|| event.getType() == IResourceChangeEvent.POST_BUILD) {
				incrementChangeCount();
				if (numChanges >= CHANGE_THRESHHOLD) {
					checkOperationHistory();
				}
			}
		};
	}

	/**
	 * Get a change listener for listening to operation history changes.
	 *
	 * @return the resource change listeners
	 */
	private IOperationHistoryListener getOperationHistoryListener() {
		return event -> {
			if (!event.getOperation().hasContext(
					WorkspaceUndoUtil.getWorkspaceUndoContext())) {
				return;
			}
			switch (event.getEventType()) {
			case OperationHistoryEvent.ABOUT_TO_EXECUTE:
			case OperationHistoryEvent.ABOUT_TO_UNDO:
			case OperationHistoryEvent.ABOUT_TO_REDO:
				operationInProgress = event.getOperation();
				break;
			case OperationHistoryEvent.DONE:
			case OperationHistoryEvent.UNDONE:
			case OperationHistoryEvent.REDONE:
				resetChangeCount();
				operationInProgress = null;
				break;
			case OperationHistoryEvent.OPERATION_NOT_OK:
				operationInProgress = null;
				break;
			}
		};
	}

	/**
	 * Shutdown the workspace undo monitor. Unhooks the listeners.
	 */
	public void shutdown() {
		if (Policy.DEBUG_UNDOMONITOR) {
		}

		if (resourceListener != null) {
			ResourcesPlugin.getWorkspace().removeResourceChangeListener(
					resourceListener);
		}
		if (historyListener != null) {
			getOperationHistory().removeOperationHistoryListener(
					historyListener);
		}
	}

	/**
	 * Get the operation history.
	 */
	private IOperationHistory getOperationHistory() {
		return PlatformUI.getWorkbench().getOperationSupport()
				.getOperationHistory();
	}

	/**
	 * Check the pending undoable operation to see if it is still valid.
	 */
	private void checkOperationHistory() {
		if (Policy.DEBUG_UNDOMONITOR) {
		}
		IUndoableOperation currentOp = getOperationHistory().getUndoOperation(
				WorkspaceUndoUtil.getWorkspaceUndoContext());
		if (currentOp == null) {
			resetChangeCount();
			return;
		}
		if (!currentOp.canUndo()) {
			flushWorkspaceHistory(currentOp);
			return;
		}
		if (currentOp instanceof IAdvancedUndoableOperation
				&& currentOp instanceof IAdvancedUndoableOperation2) {
			((IAdvancedUndoableOperation2) currentOp).setQuietCompute(true);
			IStatus status;
			try {
				status = ((IAdvancedUndoableOperation) currentOp)
						.computeUndoableStatus(null);
			} catch (ExecutionException e) {
				status = Status.OK_STATUS;
			}
			((IAdvancedUndoableOperation2) currentOp).setQuietCompute(false);
			if (status.getSeverity() == IStatus.ERROR) {
				flushWorkspaceHistory(currentOp);
			}
		}
		resetChangeCount();
	}

	/**
	 * Flush the undo and redo history for the workspace undo context.
	 */
	private void flushWorkspaceHistory(IUndoableOperation op) {
		if (Policy.DEBUG_UNDOMONITOR) {
			System.out.println(DEBUG_PREFIX
		}
		getOperationHistory().dispose(
				WorkspaceUndoUtil.getWorkspaceUndoContext(), true, true, false);
	}

	/**
	 * Reset the workspace change count
	 */
	private void resetChangeCount() {
		numChanges = 0;
		if (Policy.DEBUG_UNDOMONITOR) {
		}
	}

	/**
	 * Increment the workspace change count
	 */
	private void incrementChangeCount() {
		numChanges++;
		if (Policy.DEBUG_UNDOMONITOR) {
			System.out
					.println(DEBUG_PREFIX
		}
	}
}
