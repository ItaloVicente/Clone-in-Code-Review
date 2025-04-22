package org.eclipse.jgit.api;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.errors.NoWorkTreeException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.IndexDiff;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.submodule.SubmoduleWalk.IgnoreSubmoduleMode;
import org.eclipse.jgit.treewalk.FileTreeIterator;
import org.eclipse.jgit.treewalk.WorkingTreeIterator;
import org.eclipse.jgit.treewalk.filter.PathFilterGroup;

public class StatusCommand extends GitCommand<Status> {
	private WorkingTreeIterator workingTreeIt;
	private List<String> paths = null;
	private ProgressMonitor progressMonitor = null;

	private IgnoreSubmoduleMode ignoreSubmoduleMode = null;

	protected StatusCommand(Repository repo) {
		super(repo);
	}

	public StatusCommand setIgnoreSubmodules(IgnoreSubmoduleMode mode) {
		ignoreSubmoduleMode = mode;
		return this;
	}

	public StatusCommand addPath(String path) {
		if (paths == null)
			paths = new LinkedList<>();
		paths.add(path);
		return this;
	}

	public List<String> getPaths() {
		return paths;
	}

	@Override
	public Status call() throws GitAPIException
		if (workingTreeIt == null)
			workingTreeIt = new FileTreeIterator(repo);

		try {
			IndexDiff diff = new IndexDiff(repo
			if (ignoreSubmoduleMode != null)
				diff.setIgnoreSubmoduleMode(ignoreSubmoduleMode);
			if (paths != null)
				diff.setFilter(PathFilterGroup.createFromStrings(paths));
			if (progressMonitor == null)
				diff.diff();
			else
				diff.diff(progressMonitor
						ProgressMonitor.UNKNOWN
			return new Status(diff);
		} catch (IOException e) {
			throw new JGitInternalException(e.getMessage()
		}
	}

	public StatusCommand setWorkingTreeIt(WorkingTreeIterator workingTreeIt) {
		this.workingTreeIt = workingTreeIt;
		return this;
	}

	public StatusCommand setProgressMonitor(ProgressMonitor progressMonitor) {
		this.progressMonitor = progressMonitor;
		return this;
	}
}
