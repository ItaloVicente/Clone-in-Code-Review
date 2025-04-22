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

public class BlameGenerator {

	private Repository repository;

	private String path;

	private AnyObjectId start;

	private AnyObjectId end;

	private DiffAlgorithm diffAlgorithm = new HistogramDiff();

	private RawTextComparator textComparator = RawTextComparator.DEFAULT;

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

	private List<Line> getLines(int count
		List<Line> lines = new ArrayList<Line>(count);
		for (int i = 0; i < count; i++)
			lines.add(new Line(commit
		return lines;
	}

	private Line[] merge(RevCommit commit
			EditList diffs) {
		Line[] aLines = new Line[size];
		int aIndex = 0;
		int bIndex = 0;

		for (Edit diff : diffs) {
			while (aIndex < diff.getBeginA()) {
				Line line = bLines[bIndex++];
				if (line != null)
					line.setStart(commit);
				aLines[aIndex++] = line;
			}
			aIndex = diff.getEndA();
			bIndex = diff.getEndB();
		}

		while (bIndex < bLines.length) {
			Line line = bLines[bIndex++];
			if (line != null)
				line.setStart(commit);
			aLines[aIndex++] = line;
		}
		return aLines;
	}

	private String getPath(RevCommit commit
			TreeWalk treeWalk
		if (commit.getParentCount() == 1) {
			treeWalk.reset(commit.getParent(0).getTree()
			detector.reset();
			detector.addAll(DiffEntry.scan(treeWalk));
			for (DiffEntry ent : detector.compute())
				if ((ent.getChangeType() == ChangeType.RENAME || ent
						.getChangeType() == ChangeType.COPY)
						&& ent.getNewPath().equals(lastPath))
					return ent.getOldPath();
		}
		return lastPath;
	}

	public List<Line> generate() throws IOException {
		RevWalk revWalk = new RevWalk(repository);

		String lastPath = path;
		RawText lastText = null;
		Line[] workingLines = null;

		List<Line> lines = null;
		try {
			ObjectReader reader = revWalk.getObjectReader();
			revWalk.setRetainBody(true);
			revWalk.setTreeFilter(FollowFilter.create(path));
			TreeWalk treeWalk = new TreeWalk(reader);
			treeWalk.setFilter(TreeFilter.ANY_DIFF);
			RenameDetector detector = new RenameDetector(repository);

			AnyObjectId head = start != null ? start : repository
					.resolve(Constants.HEAD);
			revWalk.markStart(revWalk.parseCommit(head));
			if (end != null)
				revWalk.markUninteresting(revWalk.parseCommit(end));

			for (RevCommit commit : revWalk) {

				TreeWalk blobWalk = TreeWalk.forPath(reader
						commit.getTree());
				if (blobWalk == null)
					break;

				RawText currentText = getRawText(blobWalk.getObjectId(0));
				if (lastText != null) {
					EditList diffs = diffAlgorithm.diff(textComparator
							currentText
					workingLines = merge(commit
							workingLines
				} else {
					lines = getLines(currentText.size()
					workingLines = lines.toArray(new Line[lines.size()]);
				}
				lastText = currentText;
				lastPath = getPath(commit
			}
		} finally {
			revWalk.release();
		}
		return lines;
	}

	public Repository getRepository() {
		return repository;
	}

	public String getPath() {
		return path;
	}

}
