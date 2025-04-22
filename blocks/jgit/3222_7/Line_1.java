package org.eclipse.jgit.api;

import java.io.IOException;

import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.blame.RevisionBuilder;
import org.eclipse.jgit.blame.RevisionContainer;
import org.eclipse.jgit.diff.DiffAlgorithm;
import org.eclipse.jgit.diff.RawTextComparator;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;

public class LineHistoryCommand extends GitCommand<RevisionContainer> {

	private String path;

	private DiffAlgorithm diffAlgorithm;

	private RawTextComparator textComparator;

	private ObjectId startCommit;

	public LineHistoryCommand(Repository repo) {
		super(repo);
	}

	public LineHistoryCommand setFilePath(String filePath) {
		this.path = filePath;
		return this;
	}

	public LineHistoryCommand setDiffAlgorithm(DiffAlgorithm diffAlgorithm) {
		this.diffAlgorithm = diffAlgorithm;
		return this;
	}

	public LineHistoryCommand setTextComparator(RawTextComparator textComparator) {
		this.textComparator = textComparator;
		return this;
	}

	public LineHistoryCommand setStartCommit(ObjectId startCommit) {
		this.startCommit = startCommit;
		return this;
	}

	public RevisionContainer call() throws JGitInternalException {
		checkCallable();
		RevisionBuilder builder = new RevisionBuilder(repo
		try {
			builder.setDiffAlgorithm(diffAlgorithm);
			builder.setTextComparator(textComparator);
			builder.setStart(startCommit);
			return builder.build();
		} catch (IOException e) {
			throw new JGitInternalException(e.getMessage()
		}
	}
}
