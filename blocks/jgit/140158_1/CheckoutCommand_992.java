package org.eclipse.jgit.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.blame.BlameGenerator;
import org.eclipse.jgit.blame.BlameResult;
import org.eclipse.jgit.diff.DiffAlgorithm;
import org.eclipse.jgit.diff.RawText;
import org.eclipse.jgit.diff.RawTextComparator;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.CoreConfig.AutoCRLF;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.treewalk.WorkingTreeOptions;
import org.eclipse.jgit.util.IO;
import org.eclipse.jgit.util.io.AutoLFInputStream;

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
		reverseEndCommits = new ArrayList<>(end);
		return this;
	}

	@Override
	public BlameResult call() throws GitAPIException {
		checkCallable();
		try (BlameGenerator gen = new BlameGenerator(repo
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
					if (repo.getFS().isFile(inTree)) {
						RawText rawText = getRawText(inTree);
						gen.push(null
					}
				}
			}
			return gen.computeBlameResult();
		} catch (IOException e) {
			throw new JGitInternalException(e.getMessage()
		}
	}

	private RawText getRawText(File inTree) throws IOException
			FileNotFoundException {
		RawText rawText;

		WorkingTreeOptions workingTreeOptions = getRepository().getConfig()
				.get(WorkingTreeOptions.KEY);
		AutoCRLF autoCRLF = workingTreeOptions.getAutoCRLF();
		switch (autoCRLF) {
		case FALSE:
		case INPUT:
			rawText = new RawText(inTree);
			break;
		case TRUE:
			try (AutoLFInputStream in = new AutoLFInputStream(
					new FileInputStream(inTree)
				rawText = new RawText(toByteArray(in
			}
			break;
		default:
			throw new IllegalArgumentException(
		}
		return rawText;
	}

	private static byte[] toByteArray(InputStream source
			throws IOException {
		byte[] buffer = new byte[upperSizeLimit];
		try {
			int read = IO.readFully(source
			if (read == upperSizeLimit)
				return buffer;
			else {
				byte[] copy = new byte[read];
				System.arraycopy(buffer
				return copy;
			}
		} finally {
			source.close();
		}
	}
}
