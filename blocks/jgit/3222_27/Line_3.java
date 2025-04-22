package org.eclipse.jgit.blame;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.jgit.JGitText;
import org.eclipse.jgit.diff.DiffAlgorithm;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffEntry.ChangeType;
import org.eclipse.jgit.diff.Edit;
import org.eclipse.jgit.diff.EditList;
import org.eclipse.jgit.diff.HistogramDiff;
import org.eclipse.jgit.diff.RawText;
import org.eclipse.jgit.diff.RawTextComparator;
import org.eclipse.jgit.diff.RenameDetector;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.FollowFilter;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.TreeFilter;

public class BlameGenerator {

	private static class Session {

		private RevWalk walk;

		private RevCommit commit;

		private String path;

		private RawText text;

		private Line[] lines;

		private boolean merges;
	}

	private final Repository repository;

	private final String path;

	private AnyObjectId start;

	private AnyObjectId end;

	private DiffAlgorithm diffAlgorithm = new HistogramDiff();

	private RawTextComparator textComparator = RawTextComparator.DEFAULT;

	private final Session session = new Session();

	public BlameGenerator(Repository repository
		this.repository = repository;
		this.path = path;
	}

	public BlameGenerator setStart(ObjectId commitId) {
		start = commitId;
		return this;
	}

	public BlameGenerator setEnd(ObjectId commitId) {
		end = commitId;
		return this;
	}

	public BlameGenerator setDiffAlgorithm(DiffAlgorithm algorithm) {
		diffAlgorithm = algorithm;
		return this;
	}

	public BlameGenerator setTextComparator(RawTextComparator comparator) {
		textComparator = comparator;
		return this;
	}

	protected RawText getRawText(ObjectId blob) throws IOException {
		ObjectLoader loader = repository.open(blob
		return new RawText(loader.getCachedBytes(Integer.MAX_VALUE));
	}

	private void createLines(RevCommit commit) {
		int count = session.text.size();
		Line[] lines = new Line[count];
		for (int i = 0; i < count; i++)
			lines[i] = new Line(commit
		session.lines = lines;
	}

	private void merge(RevCommit commit
		Line[] aLines = new Line[size];
		Line[] bLines = session.lines;
		boolean merges = false;
		int aIndex = 0;
		int bIndex = 0;

		for (Edit diff : diffs) {
			while (aIndex < diff.getBeginA()) {
				Line line = bLines[bIndex++];
				if (line != null) {
					line.setCommit(commit);
					merges = true;
				}
				aLines[aIndex++] = line;
			}
			aIndex = diff.getEndA();
			while (bIndex < diff.getEndB()) {
				Line line = bLines[bIndex++];
				if (line != null)
					line.markBound();
			}
			bIndex = diff.getEndB();
		}

		while (bIndex < bLines.length) {
			Line line = bLines[bIndex++];
			if (line != null) {
				line.setCommit(commit);
				merges = true;
			}
			aLines[aIndex++] = line;
		}

		session.lines = aLines;
		session.merges = merges;
	}

	private void updatePath(RevCommit commit) throws IOException {
		TreeWalk treeWalk = new TreeWalk(session.walk.getObjectReader());
		try {
			treeWalk.setFilter(TreeFilter.ANY_DIFF);
			RenameDetector detector = new RenameDetector(repository);
			treeWalk.reset(commit.getTree()
			detector.addAll(DiffEntry.scan(treeWalk));
			for (DiffEntry ent : detector.compute())
				if ((ent.getChangeType() == ChangeType.RENAME || ent
						.getChangeType() == ChangeType.COPY)
						&& ent.getNewPath().equals(session.path)) {
					session.path = ent.getOldPath();
					break;
				}
		} finally {
			treeWalk.release();
		}
	}

	private ObjectId getBlob(RevCommit commit) throws IOException {
		TreeWalk walk = TreeWalk.forPath(session.walk.getObjectReader()
				session.path
		return walk != null ? walk.getObjectId(0) : null;
	}

	public List<Line> start() throws IOException {
		if (session.walk != null)
			throw new IllegalStateException(
					JGitText.get().blameHasAlreadyBeenStarted);

		RevWalk revWalk = new RevWalk(repository);
		revWalk.setRetainBody(true);
		revWalk.setTreeFilter(FollowFilter.create(path));
		session.walk = revWalk;

		AnyObjectId head = start != null ? start : repository
				.resolve(Constants.HEAD);
		revWalk.markStart(revWalk.parseCommit(head));
		if (end != null)
			revWalk.markUninteresting(revWalk.parseCommit(end));

		RevCommit commit = revWalk.next();
		if (commit == null)
			return null;

		session.path = path;
		session.commit = commit;
		ObjectId blob = getBlob(commit);
		if (blob == null)
			return null;
		session.text = getRawText(blob);
		createLines(commit);

		return Collections.unmodifiableList(Arrays.asList(session.lines));
	}

	public boolean next() throws IOException {
		if (session.walk == null)
			throw new IllegalStateException(
					JGitText.get().blameHasNotBeenStarted);

		RevCommit commit = session.walk.next();
		if (commit == null) {
			markSessionBound();
			return false;
		}
		ObjectId blob = getBlob(commit);
		if (blob == null) {
			updatePath(commit);
			blob = getBlob(commit);
		}
		if (blob == null) {
			markSessionBound();
			return false;
		}

		RawText text = getRawText(blob);
		EditList diffs = diffAlgorithm.diff(textComparator
		merge(commit
		session.text = text;
		session.commit = commit;
		if (!session.merges)
			markSessionBound();
		return session.merges;
	}

	private void markSessionBound() {
		for (Line line : session.lines)
			if (line != null)
				line.markBound();
	}

	public BlameGenerator release() {
		if (session.walk != null) {
			session.walk.release();
			session.walk = null;
		}
		session.lines = null;
		session.merges = false;
		session.path = null;
		session.text = null;
		return this;
	}

	public Repository getRepository() {
		return repository;
	}

	public String getPath() {
		return path;
	}
}
