package org.eclipse.jgit.api;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.blame.BlameGenerator;
import org.eclipse.jgit.blame.BlameResult;
import org.eclipse.jgit.diff.DiffAlgorithm;
import org.eclipse.jgit.diff.RawText;
import org.eclipse.jgit.diff.RawTextComparator;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;

public class BlameCommand extends GitCommand<BlameResult> {

	private String path;

	private DiffAlgorithm diffAlgorithm;

	private RawTextComparator textComparator;

	private ObjectId startCommit;

	private Boolean followFileRenames;

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

	public BlameCommand setFollowFileRenames(boolean follow) {
		followFileRenames = Boolean.valueOf(follow);
		return this;
	}

	public BlameResult call() throws JGitInternalException {
		checkCallable();
		BlameGenerator generator = new BlameGenerator(repo
		try {
			if (diffAlgorithm != null)
				generator.setDiffAlgorithm(diffAlgorithm);
			if (textComparator != null)
				generator.setTextComparator(textComparator);
			if (followFileRenames != null)
				generator.setFollowFileRenames(followFileRenames.booleanValue());

			if (startCommit != null)
				generator.push(null
			else
				generator.push(null

			if (!repo.isBare()) {
				DirCache dc = repo.readDirCache();
				int entry = dc.findEntry(path);
				if (0 <= entry)
					generator.push(null

				File inTree = new File(repo.getWorkTree()
				if (inTree.isFile())
					generator.push(null
			}

			return generator.computeBlameResult();
		} catch (IOException e) {
			throw new JGitInternalException(e.getMessage()
		} finally {
			generator.release();
		}
	}
}
