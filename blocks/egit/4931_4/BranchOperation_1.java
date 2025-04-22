package org.eclipse.egit.core.op;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jgit.lib.Repository;

abstract class BaseOperation implements IEGitOperation {

	protected final Repository repository;

	protected Collection<PreExecuteTask> preTasks;

	protected Collection<PostExecuteTask> postTasks;

	BaseOperation(final Repository repository) {
		this.repository = repository;
	}

	protected void preExecute(IProgressMonitor monitor) throws CoreException {
		synchronized (this) {
			if (preTasks != null)
				for (PreExecuteTask task : preTasks)
					task.preExecute(repository, monitor);
		}
	}

	protected void postExecute(IProgressMonitor monitor) throws CoreException {
		synchronized (this) {
			if (postTasks != null)
				for (PostExecuteTask task : postTasks)
					task.postExecute(repository, monitor);
		}
	}

	public synchronized void addPreExecuteTask(final PreExecuteTask task) {
		if (preTasks == null)
			preTasks = new ArrayList<PreExecuteTask>();
		preTasks.add(task);
	}

	public synchronized void addPostExecuteTask(PostExecuteTask task) {
		if (postTasks == null)
			postTasks = new ArrayList<PostExecuteTask>();
		postTasks.add(task);
	}
}
