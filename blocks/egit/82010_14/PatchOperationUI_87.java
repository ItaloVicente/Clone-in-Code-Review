package org.eclipse.egit.ui.internal.operations;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.Map;

import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.egit.core.internal.indexdiff.IndexDiffCache;
import org.eclipse.egit.core.internal.indexdiff.IndexDiffCacheEntry;
import org.eclipse.egit.core.internal.util.ResourceUtil;
import org.eclipse.egit.core.op.IgnoreOperation;
import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.internal.UIText;
import org.eclipse.egit.ui.internal.decorators.GitLightweightDecorator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

public class IgnoreOperationUI {

	private static final int WARNING_THRESHOLD = 500;

	private final Collection<IPath> paths;

	public IgnoreOperationUI(final Collection<IPath> paths) {
		this.paths = paths;
	}

	public void run() {
		if (paths.size() > WARNING_THRESHOLD) {
			Shell shell = PlatformUI.getWorkbench()
					.getModalDialogShellProvider().getShell();
			if (!MessageDialog.openQuestion(shell,
					MessageFormat.format(
							UIText.IgnoreActionHandler_manyFilesToBeIgnoredTitle,
							Integer.toString(paths.size())),
					UIText.IgnoreActionHandler_manyFilesToBeIgnoredQuestion)) {
				return;
			}
		}
		final IgnoreOperation operation = new IgnoreOperation(paths);
		String jobname = UIText.IgnoreActionHandler_addToGitignore;
		Job job = new WorkspaceJob(jobname) {

			@Override
			public IStatus runInWorkspace(IProgressMonitor monitor)
					throws CoreException {
				try {
					try {
						operation.execute(monitor);
					} catch (CoreException e) {
						return Activator.createErrorStatus(
								e.getStatus().getMessage(), e);
					}
					if (operation.isGitignoreOutsideWSChanged()) {
						refresh();
					}
					if (monitor.isCanceled()) {
						throw new OperationCanceledException();
					}
					return Status.OK_STATUS;
				} finally {
					monitor.done();
				}
			}
		};
		job.setUser(true);
		job.setRule(operation.getSchedulingRule());
		job.schedule();
	}

	private void refresh() {
		Map<Repository, Collection<String>> pathsByRepository =
				ResourceUtil.splitPathsByRepository(paths);
		for (Repository repository : pathsByRepository.keySet()) {
			IndexDiffCache cache = org.eclipse.egit.core.Activator.getDefault().getIndexDiffCache();
			IndexDiffCacheEntry entry = cache.getIndexDiffCacheEntry(repository);
			if (entry != null)
				entry.refresh();
		}
		GitLightweightDecorator.refresh();
	}
}
