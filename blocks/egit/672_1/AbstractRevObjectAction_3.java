package org.eclipse.egit.ui.internal.actions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.egit.core.op.IEGitOperation;
import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.internal.history.RevObjectSelectionProvider;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

public abstract class AbstractRevCommitOperationAction implements IObjectActionDelegate {
	protected IWorkbenchPart wp;

	private IEGitOperation op;

	private List selection;

	public void selectionChanged(final IAction act, final ISelection sel) {
		if (sel instanceof IStructuredSelection && !sel.isEmpty()) {
			selection = ((IStructuredSelection) sel).toList();
		} else {
			selection = Collections.EMPTY_LIST;
		}
	}

	public void setActivePart(final IAction act, final IWorkbenchPart part) {
		wp = part;
	}

	protected abstract IEGitOperation createOperation(final List<RevCommit> selection);

	protected abstract String getJobName();

	protected void postOperation() {
	}

	public void run(final IAction act) {
		op = createOperation(getSelectedCommits());
		if(op==null)
			return;
		String jobname = getJobName();
		Job job = new Job(jobname) {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				try {
					op.execute(monitor);
					postOperation();
				} catch (CoreException e) {
					return Activator.createErrorStatus(e.getStatus()
							.getMessage(), e);
				}
				return Status.OK_STATUS;
			}
		};
		job.setUser(true);
		job.setRule(op.getSchedulingRule());
		job.schedule();
	}

	private List<RevCommit> getSelectedCommits() {
		List<RevCommit> commits = new ArrayList<RevCommit>();
		for(Object object: selection) {
			if(object instanceof RevCommit)
				commits.add((RevCommit) object);
		}
		return commits;
	}

	protected Repository getActiveRepository() {
		RevObjectSelectionProvider selectionProvider = (RevObjectSelectionProvider) wp
				.getSite().getSelectionProvider();
		return selectionProvider.getActiveRepository();
	}
}
