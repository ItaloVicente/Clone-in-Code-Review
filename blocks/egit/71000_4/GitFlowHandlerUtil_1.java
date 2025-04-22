package org.eclipse.egit.gitflow.ui.internal.actions;

import static org.eclipse.core.runtime.Status.OK_STATUS;
import static org.eclipse.egit.ui.Activator.createErrorStatus;
import static org.eclipse.egit.ui.JobFamilies.DISCARD_CHANGES;
import static org.eclipse.egit.ui.internal.UIText.DiscardChangesAction_discardChanges;
import static org.eclipse.egit.ui.internal.operations.GitScopeUtil.getRelatedChanges;

import java.io.IOException;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.egit.core.op.DiscardChangesOperation;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.handlers.HandlerUtil;

public class DevelopReplaceHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchPart part = HandlerUtil.getActivePartChecked(event);

		final DiscardChangesOperation operation;
		try {
			operation = createOperation(part, event);
		} catch (IOException e) {
			throw new ExecutionException(e.getMessage(), e);
		}

		if (operation == null) {
			return null;
		}
		Job job = new WorkspaceJob(DiscardChangesAction_discardChanges) {
			@Override
			public IStatus runInWorkspace(IProgressMonitor monitor) {
				try {
					operation.execute(monitor);
				} catch (CoreException e) {
					return createErrorStatus(e.getStatus().getMessage(), e);
				}
				return OK_STATUS;
			}

			@Override
			public boolean belongsTo(Object family) {
				if (DISCARD_CHANGES.equals(family)) {
					return true;
				}
				return super.belongsTo(family);
			}
		};
		job.setUser(true);
		job.setRule(operation.getSchedulingRule());
		job.schedule();
		return null;
	}

	private @Nullable DiscardChangesOperation createOperation(
			IWorkbenchPart part, ExecutionEvent event)
					throws ExecutionException, IOException {
		IResource[] selectedResources = GitFlowHandlerUtil.gatherResourceToOperateOn(event);
		String revision;
		try {
			revision = GitFlowHandlerUtil.gatherRevision(event);
		} catch (OperationCanceledException e) {
			return null;
		}

		IResource[] resourcesInScope;
		try {
			resourcesInScope = getRelatedChanges(part, selectedResources);
		} catch (InterruptedException e) {
			return null;
		}

		return new DiscardChangesOperation(resourcesInScope, revision);
	}
}
