package org.eclipse.jgit.api;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.blame.Revision;
import org.eclipse.jgit.blame.RevisionBuilder;
import org.eclipse.jgit.diff.DiffAlgorithm;
import org.eclipse.jgit.diff.RawTextComparator;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;

public class LineHistoryCommand extends GitCommand<List<Revision>> {

	private String path;

	private DiffAlgorithm diffAlgorithm;

	private RawTextComparator textComparator;

	private ObjectId startCommit;

	private ObjectId endCommit;

	private boolean latestOnly;

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

	public LineHistoryCommand setStartCommit(ObjectId commit) {
		this.startCommit = commit;
		return this;
	}

	public LineHistoryCommand setEndCommit(ObjectId commit) {
		this.endCommit = commit;
		return this;
	}

	public LineHistoryCommand setLatestOnly(boolean latest) {
		this.latestOnly = latest;
		return this;
	}

	public List<Revision> call() throws JGitInternalException {
		checkCallable();
		RevisionBuilder builder = new RevisionBuilder(repo
		try {
			if (diffAlgorithm != null)
				builder.setDiffAlgorithm(diffAlgorithm);
			if (textComparator != null)
				builder.setTextComparator(textComparator);
			builder.setStart(startCommit);
			builder.setEnd(endCommit);
			if (latestOnly)
				return Collections.singletonList(builder.buildLatest());
			else
				return builder.buildAll();
		} catch (IOException e) {
			throw new JGitInternalException(e.getMessage()
		}
	}
}
