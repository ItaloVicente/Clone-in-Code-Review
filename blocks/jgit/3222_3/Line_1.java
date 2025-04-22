package org.eclipse.jgit.api;

import java.io.IOException;

import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lines.RevisionBuilder;
import org.eclipse.jgit.lines.RevisionContainer;

public class LineHistoryCommand extends GitCommand<RevisionContainer> {

	private String path;

	protected LineHistoryCommand(Repository repo) {
		super(repo);
	}

	public LineHistoryCommand setFilePath(String filePath) {
		this.path = filePath;
		return this;
	}

	public RevisionContainer call() throws JGitInternalException {
		checkCallable();
		try {
			return new RevisionBuilder(repo
		} catch (IOException e) {
			throw new JGitInternalException(e.getMessage()
		}
	}

}
