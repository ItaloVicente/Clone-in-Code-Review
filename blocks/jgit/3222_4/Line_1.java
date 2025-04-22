package org.eclipse.jgit.api;

import java.io.IOException;

import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.blame.RevisionBuilder;
import org.eclipse.jgit.blame.RevisionContainer;
import org.eclipse.jgit.lib.Repository;

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
		RevisionBuilder builder = new RevisionBuilder(repo
		try {
			return builder.build();
		} catch (IOException e) {
			throw new JGitInternalException(e.getMessage()
		}
	}
}
