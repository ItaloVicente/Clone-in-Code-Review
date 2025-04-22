package org.eclipse.egit.core.op;

import static org.eclipse.egit.core.project.RepositoryMapping.findRepositoryMapping;
import static org.eclipse.jgit.lib.Constants.HEAD;

import java.io.IOException;
import java.util.Collection;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.egit.core.Activator;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ResetCommand;
import org.eclipse.jgit.lib.Repository;

public class RemoveFromIndexOperation implements IEGitOperation {

	private final Repository repo;

	private final Collection<String> paths;

	public RemoveFromIndexOperation(Repository repo, Collection<String> paths) {
		this.repo = repo;
		this.paths = paths;
	}

	public void execute(IProgressMonitor m) throws CoreException {
		IProgressMonitor monitor;
		if (m == null)
			monitor = new NullProgressMonitor();
		else
			monitor = m;

		ResetCommand resetCommand = new Git(repo).reset();
		resetCommand.setRef(HEAD);
		monitor.worked(1);

		for (String path : paths) {
			resetCommand.addPath(path);
			monitor.worked(1);
		}

		try {
			resetCommand.call();
			monitor.worked(1);
		} catch (IOException e) {
			Activator.logError(e.getMessage(), e);
		} finally {
			monitor.done();
			findRepositoryMapping(repo).fireRepositoryChanged();
		}
	}

	public ISchedulingRule getSchedulingRule() {
		return null;
	}

}
