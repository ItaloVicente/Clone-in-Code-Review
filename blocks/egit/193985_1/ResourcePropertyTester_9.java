
package org.eclipse.egit.ui.internal;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.compare.structuremergeviewer.IDiffContainer;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.internal.merge.GitMergeEditorInput;
import org.eclipse.jgit.lib.Repository;

public class DiffContainerJob extends Job {

	private IDiffContainer diffCont = null;

	private GitMergeEditorInput gitMergeInput = null;

	public DiffContainerJob(String name, GitMergeEditorInput input) {
		super(name);
		this.gitMergeInput = input;
	}

	public IDiffContainer getDiffContainer() {
		return diffCont;
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		try {
			diffCont = gitMergeInput.getDiffContainer(monitor);
		} catch (InvocationTargetException | InterruptedException e) {
			String errorMessage = "Failed to retrieve diff contents."; //$NON-NLS-1$
			IStatus error = new Status(IStatus.ERROR, Activator.PLUGIN_ID,
					errorMessage, e);
			return error;
		}
		return Status.OK_STATUS;
	}

	public Repository getRepository() {
		return gitMergeInput.getRepository();
	}

}
