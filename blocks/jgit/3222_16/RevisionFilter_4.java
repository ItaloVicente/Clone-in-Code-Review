package org.eclipse.jgit.blame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.FollowFilter;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.TreeFilter;
import org.eclipse.jgit.util.IO;

public class RevisionBuilder {

	private Repository repository;

	private String path;

	private AnyObjectId start;

	private AnyObjectId end;

	private DiffAlgorithm diffAlgorithm = new HistogramDiff();

	private RawTextComparator textComparator = RawTextComparator.DEFAULT;

	public RevisionBuilder(Repository repository
		this.repository = repository;
		this.path = path;
	}

	public RevisionBuilder setStart(ObjectId commitId) {
		start = commitId;
		return this;
	}

	public RevisionBuilder setEnd(ObjectId commitId) {
		end = commitId;
		return this;
	}

	public RevisionBuilder setDiffAlgorithm(DiffAlgorithm algorithm) {
		diffAlgorithm = algorithm;
		return this;
	}

	public RevisionBuilder setTextComparator(RawTextComparator comparator) {
		textComparator = comparator;
		return this;
	}

	protected RawText getRawText(ObjectId blob) throws IOException {
		ObjectLoader loader = repository.open(blob
		byte[] contents;
		if (loader.isLarge())
			contents = IO.readWholeStream(loader.openStream()
					(int) loader.getSize()).array();
		else
			contents = loader.getCachedBytes();
		return new RawText(contents);
	}

	protected Revision addLines(Revision revision) {
		for (int i = 0; i < revision.getLineCount(); i++)
			revision.addLine(new Line(revision.getCommit()));
		return revision;
	}

	private void mergeLines(Revision current
		int currentLine = 0;
		for (Edit edit : edits) {
			while (currentLine < edit.getBeginA())
				previous.addLine(current.getLine(currentLine++));
			for (int i = edit.getBeginB(); i < edit.getEndB(); i++)
				previous.addLine(new Line(previous.getCommit()));
			currentLine = edit.getEndA();
		}
		while (currentLine < current.getLineCount())
			previous.addLine(current.getLine(currentLine++));
	}

	public Revision buildLatest() throws IOException {
		final Revision[] last = new Revision[] { null };
		build(new RevisionFilter() {

			public boolean include(Revision revision) {
				if (last[0] == null)
					last[0] = revision;
				return true;
			}
		});
		return last[0];
	}

	public List<Revision> buildAll() throws IOException {
		final List<Revision> revisions = new ArrayList<Revision>();
		build(new RevisionFilter() {

			public boolean include(Revision revision) {
				revisions.add(0
				return true;
			}
		});
		return revisions;
	}

	public void build(RevisionFilter filter) throws IOException {
		RevWalk revWalk = new RevWalk(repository);
		ObjectReader reader = revWalk.getObjectReader();
		revWalk.setRetainBody(true);
		revWalk.setTreeFilter(FollowFilter.create(path));

		TreeWalk treeWalk = new TreeWalk(reader);
		treeWalk.setFilter(TreeFilter.ANY_DIFF);
		RenameDetector detector = new RenameDetector(repository);

		String currentPath = path;
		RawText nextText = null;
		Revision next = null;
		try {
			AnyObjectId head = start != null ? start : repository
					.resolve(Constants.HEAD);
			revWalk.markStart(revWalk.parseCommit(head));
			if (end != null)
				revWalk.markUninteresting(revWalk.parseCommit(end));
			for (RevCommit commit : revWalk) {
				TreeWalk blobWalk = TreeWalk.forPath(reader
						commit.getTree());
				if (blobWalk != null) {
					ObjectId blobId = blobWalk.getObjectId(0);
					RawText currentText = getRawText(blobId);
					Revision revision = new Revision(currentPath
							currentText.size());
					revision.setCommit(commit);
					revision.setBlob(blobId);
					if (!filter.include(revision))
						break;
					if (next != null)
						mergeLines(next
								textComparator
					else
						addLines(revision);
					next = revision;
					nextText = currentText;
				}
				if (commit.getParentCount() == 1) {
					treeWalk.reset(commit.getParent(0).getTree()
							commit.getTree());
					detector.reset();
					detector.addAll(DiffEntry.scan(treeWalk));
					for (DiffEntry ent : detector.compute())
						if (ent.getChangeType() == ChangeType.RENAME
								&& ent.getNewPath().equals(currentPath)) {
							currentPath = ent.getOldPath();
							break;
						}
				}
			}
		} finally {
			revWalk.release();
		}
	}

	public Repository getRepository() {
		return repository;
	}

	public String getPath() {
		return path;
	}

}
