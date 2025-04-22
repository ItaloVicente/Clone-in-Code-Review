package org.eclipse.jgit.blame;

import java.io.IOException;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffEntry.ChangeType;
import org.eclipse.jgit.diff.DiffAlgorithm;
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
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.FollowFilter;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.TreeFilter;

public class RevisionBuilder {

	private Repository repository;

	private String path;

	private AnyObjectId start;

	private DiffAlgorithm diffAlgorithm;

	private RawTextComparator textComparator;

	public RevisionBuilder(Repository repository
		this.repository = repository;
		this.path = path;
	}

	public RevisionBuilder setStart(ObjectId commitId) {
		this.start = commitId;
		return this;
	}

	public RevisionBuilder setDiffAlgorithm(DiffAlgorithm algorithm) {
		this.diffAlgorithm = algorithm;
		return this;
	}

	public RevisionBuilder setTextComparator(RawTextComparator comparator) {
		this.textComparator = comparator;
		return this;
	}

	protected ObjectId getBlob(ObjectReader reader
			throws IOException {
		TreeWalk treeWalk = TreeWalk.forPath(reader
				revision.getCommit().getTree());
		return treeWalk.getObjectId(0);
	}

	protected RawText buildLines(Revision revision) throws IOException {
		ObjectLoader loader = repository.open(revision.getBlob()
				Constants.OBJ_BLOB);
		RawText text = new RawText(loader.getCachedBytes());
		int number = revision.getNumber();
		for (int i = 0; i < text.size(); i++)
			revision.addLine(new Line(number));
		return text;
	}

	private void mergeLines(Revision current
		List<Line> merges = new LinkedList<Line>();
		int currentLine = 0;
		int nextLine = 0;
		for (Edit edit : edits) {
			while (currentLine < edit.getBeginA())
				merges.add(current.getLine(currentLine++).setNumber(nextLine++));
			nextLine = edit.getEndB();
			currentLine = edit.getEndA();
		}
		while (currentLine < current.getLineCount())
			merges.add(current.getLine(currentLine++).setNumber(nextLine++));
		for (Line merge : merges)
			next.merge(merge);
	}

	protected Deque<Revision> buildRevisions() throws IOException {
		Deque<Revision> revisions = new LinkedList<Revision>();
		RevWalk revWalk = new RevWalk(repository);
		ObjectReader reader = revWalk.getObjectReader();
		revWalk.setRetainBody(true);
		revWalk.setTreeFilter(FollowFilter.create(this.path));
		TreeWalk treeWalk = new TreeWalk(reader);
		treeWalk.setFilter(TreeFilter.ANY_DIFF);
		RenameDetector detector = new RenameDetector(repository);
		String currentPath = this.path;
		try {
			AnyObjectId head = this.start != null ? this.start : repository
					.resolve(Constants.HEAD);
			revWalk.markStart(revWalk.parseCommit(head));
			for (RevCommit commit : revWalk) {
				Revision revision = new Revision(currentPath);
				revision.setCommit(commit);
				revision.setBlob(getBlob(reader
				revisions.addFirst(revision);
				if (commit.getParentCount() == 1) {
					treeWalk.reset(commit.getTree()
							.getTree());
					detector.reset();
					detector.addAll(DiffEntry.scan(treeWalk));
					for (DiffEntry ent : detector.compute())
						if (ent.getChangeType() == ChangeType.RENAME
								&& ent.getOldPath().equals(this.path))
							currentPath = ent.getNewPath();
				}
			}
		} finally {
			revWalk.release();
		}
		return revisions;
	}

	public RevisionContainer build() throws IOException {
		if (diffAlgorithm == null)
			diffAlgorithm = new HistogramDiff();
		if (textComparator == null)
			textComparator = RawTextComparator.DEFAULT;

		Deque<Revision> commits = buildRevisions();

		if (commits.isEmpty())
			return new RevisionContainer();

		RevisionContainer container = new RevisionContainer();
		Iterator<Revision> iterator = commits.iterator();
		Revision current = iterator.next();
		container.addRevision(current);
		RawText currentText = buildLines(current);

		Revision next = null;
		while (iterator.hasNext()) {
			next = iterator.next();
			container.addRevision(next);
			RawText nextText = buildLines(next);
			EditList edits = diffAlgorithm.diff(textComparator
					nextText);
			mergeLines(current
			current = next;
			currentText = nextText;
		}
		return container;
	}

	public Repository getRepository() {
		return this.repository;
	}

	public String getPath() {
		return this.path;
	}

}
