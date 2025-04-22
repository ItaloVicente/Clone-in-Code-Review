package org.eclipse.egit.core.op;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.egit.core.Activator;
import org.eclipse.egit.core.CoreText;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.lib.Repository;

public class DeleteTagOperation implements IEGitOperation {

	private final Repository repository;

	private final String tag;

	public DeleteTagOperation(final Repository repository, final String tag) {
		this.repository = repository;
		this.tag = tag;
	}

	public void execute(IProgressMonitor monitor) throws CoreException {
		try {
			Git.wrap(repository).tagDelete().setTags(tag).call();
		} catch (JGitInternalException e) {
			throw new CoreException(Activator.error(
					CoreText.DeleteTagOperation_exceptionMessage, e));
		}
	}

	public ISchedulingRule getSchedulingRule() {
		return null;
	}
}
