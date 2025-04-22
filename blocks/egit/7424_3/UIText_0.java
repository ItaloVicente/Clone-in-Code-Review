package org.eclipse.egit.core.op;

import java.io.IOException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.egit.core.Activator;
import org.eclipse.egit.core.EclipseGitProgressTransformer;
import org.eclipse.egit.core.internal.job.RuleUtil;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepository;
import org.eclipse.jgit.storage.file.GC;

public class GarbageCollectOperation implements IEGitOperation {

	private Repository repository;

	public GarbageCollectOperation(Repository repository) {
		this.repository = repository;
	}

	public void execute(IProgressMonitor monitor) throws CoreException {
		GC gc = new GC((FileRepository) repository);
		gc.setProgressMonitor(new EclipseGitProgressTransformer(
				monitor));
		try {
			gc.gc();
		} catch (IOException e) {
			throw new CoreException(new Status(IStatus.ERROR,
					Activator.getPluginId(), e.getMessage(), e));
		}
	}

	public ISchedulingRule getSchedulingRule() {
		return RuleUtil.getRule(repository);
	}

}
