package org.eclipse.jgit.api;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.blame.BlameGenerator;
import org.eclipse.jgit.blame.BlameResult;
import org.eclipse.jgit.diff.DiffAlgorithm;
import org.eclipse.jgit.diff.RawText;
import org.eclipse.jgit.diff.RawTextComparator;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;

public class BlameCommand extends GitCommand<BlameResult> {

	private String path;

	private DiffAlgorithm diffAlgorithm;

	private RawTextComparator textComparator;

	private ObjectId startCommit;

	private Collection<ObjectId> reverseEndCommits;

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

	public BlameCommand setStartCommit(AnyObjectId commit) {
		this.startCommit = commit.toObjectId();
		return this;
	}

	public BlameCommand setFollowFileRenames(boolean follow) {
		followFileRenames = Boolean.valueOf(follow);
		return this;
	}

	public BlameCommand reverse(AnyObjectId start
			throws IOException {
		return reverse(start
	}

	public BlameCommand reverse(AnyObjectId start
			throws IOException {
		startCommit = start.toObjectId();
		reverseEndCommits = new ArrayList<ObjectId>(end);
		return this;
	}

	public BlameResult call() throws JGitInternalException {
		checkCallable();
		BlameGenerator gen = new BlameGenerator(repo
		try {
			if (diffAlgorithm != null)
				gen.setDiffAlgorithm(diffAlgorithm);
			if (textComparator != null)
				gen.setTextComparator(textComparator);
			if (followFileRenames != null)
				gen.setFollowFileRenames(followFileRenames.booleanValue());

			if (reverseEndCommits != null)
				gen.reverse(startCommit
			else if (startCommit != null)
				gen.push(null
			else {
				gen.push(null
				if (!repo.isBare()) {
					DirCache dc = repo.readDirCache();
					int entry = dc.findEntry(path);
					if (0 <= entry)
						gen.push(null

					File inTree = new File(repo.getWorkTree()
					if (inTree.isFile())
						gen.push(null
				}
			}
			return gen.computeBlameResult();
		} catch (IOException e) {
			throw new JGitInternalException(e.getMessage()
		} finally {
			gen.release();
		}
	}
}
