package org.eclipse.jgit.api;

import java.io.IOException;
import java.util.List;

import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.blame.BlameGenerator;
import org.eclipse.jgit.blame.Line;
import org.eclipse.jgit.diff.DiffAlgorithm;
import org.eclipse.jgit.diff.RawTextComparator;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;

public class BlameCommand extends GitCommand<List<Line>> {

	private String path;

	private DiffAlgorithm diffAlgorithm;

	private RawTextComparator textComparator;

	private ObjectId startCommit;

	private ObjectId endCommit;

	public BlameCommand(Repository repo) {
		super(repo);
	}

	public BlameCommand setFilePath(String filePath) {
		this.path = filePath;
		return this;
	}

	public BlameCommand setDiffAlgorithm(DiffAlgorithm diffAlgorithm) {
		this.diffAlgorithm = diffAlgorithm;
		return this;
	}

	public BlameCommand setTextComparator(RawTextComparator textComparator) {
		this.textComparator = textComparator;
		return this;
	}

	public BlameCommand setStartCommit(ObjectId commit) {
		this.startCommit = commit;
		return this;
	}

	public BlameCommand setEndCommit(ObjectId commit) {
		this.endCommit = commit;
		return this;
	}

	public List<Line> call() throws JGitInternalException {
		checkCallable();
		BlameGenerator generator = new BlameGenerator(repo
		try {
			if (diffAlgorithm != null)
				generator.setDiffAlgorithm(diffAlgorithm);
			if (textComparator != null)
				generator.setTextComparator(textComparator);
			generator.setStart(startCommit);
			generator.setEnd(endCommit);
			List<Line> lines = generator.start();
			if (lines != null)
				while (generator.next())
					;
			return lines;
		} catch (IOException e) {
			throw new JGitInternalException(e.getMessage()
		} finally {
			generator.release();
		}
	}
}
