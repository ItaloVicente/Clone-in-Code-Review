
package org.eclipse.egit.ui.internal;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.compare.structuremergeviewer.IDiffContainer;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.egit.ui.internal.merge.GitMergeEditorInput;

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
			e.printStackTrace();
		}
		return Status.OK_STATUS;
	}

}
