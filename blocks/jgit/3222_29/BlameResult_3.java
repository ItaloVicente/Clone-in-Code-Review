
package org.eclipse.jgit.blame;

import java.io.IOException;

import org.eclipse.jgit.JGitText;
import org.eclipse.jgit.diff.DiffAlgorithm;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.Edit;
import org.eclipse.jgit.diff.EditList;
import org.eclipse.jgit.diff.HistogramDiff;
import org.eclipse.jgit.diff.RawText;
import org.eclipse.jgit.diff.RawTextComparator;
import org.eclipse.jgit.diff.RenameDetector;
import org.eclipse.jgit.diff.DiffEntry.ChangeType;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.FollowFilter;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevFlag;
import org.eclipse.jgit.revwalk.RevSort;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilter;
import org.eclipse.jgit.treewalk.filter.TreeFilter;

public class BlameGenerator {
	private final Repository repository;

	private final String resultPath;

	private DiffAlgorithm diffAlgorithm = new HistogramDiff();

	private RawTextComparator textComparator = RawTextComparator.DEFAULT;

	private boolean followFileRenames;

	private AnyObjectId start;

	private AnyObjectId end;

	private int remaining;

	private RevWalk walk;

	private RevCommit srcCommit;

	private String srcPath;

	private RawText srcText;

	private int[] srcLineMap;

	private EditList editList;

	private int curEdit;

	private RevCommit nextCommit;

	private String nextPath;

	private RawText nextText;

	private int[] nextLineMap;

	public BlameGenerator(Repository repository
		this.repository = repository;
		this.resultPath = path;
	}

	public Repository getRepository() {
		return repository;
	}

	public String getResultPath() {
		return resultPath;
	}

	public BlameGenerator setFollowFileRenames(boolean follow) {
		followFileRenames = follow;
		return this;
	}

	public BlameGenerator setStartRevision(ObjectId commitId) {
		start = commitId;
		return this;
	}

	public BlameGenerator setEndRevision(ObjectId commitId) {
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

	public BlameResult computeBlameResult() throws IOException {
		try {
			BlameResult r = BlameResult.create(this);
			if (r != null)
				r.computeAll();
			return r;
		} finally {
			release();
		}
	}

	public boolean next() throws IOException {
		if (walk == null && !start()) {
			release();
			return false;
		}

		curEdit++;

		if (remaining == 0) {
			release();
			return false;
		}

		for (;;) {
			if (curEdit == editList.size()) {
				if (!nextCommit())
					return false;
				continue;
			}

			Edit edit = editList.get(curEdit);
			int len = edit.getLengthB();
			if (len == 0) {
				curEdit++;

			} else {
				remaining -= len;
				return true;
			}
		}
	}

	public RevCommit getSourceCommit() {
		return srcCommit;
	}

	public PersonIdent getSourceAuthor() {
		return srcCommit.getAuthorIdent();
	}

	public PersonIdent getSourceCommitter() {
		return srcCommit.getCommitterIdent();
	}

	public String getSourcePath() {
		return srcPath;
	}

	public int getSourceStart() {
		return editList.get(curEdit).getBeginB();
	}

	public int getSourceEnd() {
		return editList.get(curEdit).getEndB();
	}

	public int getResultStart() {
		return srcLineMap[editList.get(curEdit).getBeginB()];
	}

	public int getResultEnd() {
		return srcLineMap[editList.get(curEdit).getEndB() - 1] + 1;
	}

	public int getRegionLength() {
		return editList.get(curEdit).getLengthB();
	}

	public RawText getSourceContents() {
		return srcText;
	}

	public RevCommit getResultCommit() throws IOException {
		if (walk == null)
			start();

		if (srcCommit != null)
			throw new IllegalStateException(JGitText.get().blameHasAlreadyBeenStarted);
		return nextCommit;
	}

	public RawText getResultContents() throws IOException {
		if (walk == null && !start())
			return null;

		if (srcCommit != null)
			throw new IllegalStateException(JGitText.get().blameHasAlreadyBeenStarted);
		return nextText;
	}

	private boolean start() throws IOException {
		walk = new RevWalk(repository);
		walk.setRetainBody(true);

		if (followFileRenames)
			walk.setTreeFilter(FollowFilter.create(resultPath));
		else
			walk.setTreeFilter(PathFilter.create(resultPath));

		AnyObjectId head = start != null ? start : repository.resolve(Constants.HEAD);
		if (head == null)
			return false;
		walk.markStart(walk.parseCommit(head));
		if (end != null) {
			walk.markUninteresting(walk.parseCommit(end));
			walk.sort(RevSort.BOUNDARY
		}

		nextCommit = walk.next();
		nextPath = resultPath;
		if (nextCommit == null)
			return false;

		ObjectId blob = getBlob(nextCommit
		if (blob == null)
			return false;

		nextText = getRawText(blob);
		remaining = nextText.size();

		nextLineMap = new int[nextText.size()];
		for (int i = 0; i < nextLineMap.length; i++)
			nextLineMap[i] = i;

		curEdit = -1;
		editList = new EditList();
		return true;
	}

	private boolean nextCommit() throws IOException {
		RevCommit n = walk.next();
		if (n == null && nextCommit == null)
			return false;

		srcCommit = nextCommit;
		srcPath = nextPath;
		srcText = nextText;
		srcLineMap = nextLineMap;

		if (n != null) {
			if (n.getParentCount() == 0)
				n.add(RevFlag.UNINTERESTING);

			nextCommit = n;
			nextPath = srcPath;
			ObjectId nextBlob = getBlob(nextCommit
			if (nextBlob == null) {
				if (!followFileRenames)
					return false;

				nextPath = wasPathRenamed(nextCommit);
				if (nextPath == null)
					return false;

				nextBlob = getBlob(nextCommit
				if (nextBlob == null)
					return false;
			}

			nextText = getRawText(nextBlob);
			nextLineMap = new int[nextText.size()];
			editList = diffAlgorithm.diff(textComparator
			updateLineMap(editList
		} else {
			nextCommit = null;
			editList = EditList.singleton(new Edit(0
		}
		curEdit = 0;
		return true;
	}

	private static void updateLineMap(EditList editList
		int aIdx = 0;
		int bIdx = 0;

		for (Edit edit : editList) {
			while (aIdx < edit.getBeginA())
				aMap[aIdx++] = bMap[bIdx++];

			for (; aIdx < edit.getEndA(); aIdx++)
				aMap[aIdx] = -1;
			bIdx = edit.getEndB();
		}

		while (aIdx < aMap.length)
			aMap[aIdx++] = bMap[bIdx++];
	}

	public BlameGenerator release() {
		srcCommit = null;
		srcPath = null;
		srcText = null;
		srcLineMap = null;

		editList = null;

		nextCommit = null;
		nextPath = null;
		nextText = null;
		nextLineMap = null;

		if (walk != null) {
			try {
				walk.release();
			} finally {
				walk = null;
			}
		}
		return this;
	}

	private ObjectId getBlob(RevCommit rev
		ObjectReader reader = walk.getObjectReader();
		TreeWalk tw = TreeWalk.forPath(reader
		if (tw == null)
			return null;
		if (tw.getFileMode(0).getObjectType() != Constants.OBJ_BLOB)
			return null;
		return tw.getObjectId(0);
	}

	private RawText getRawText(ObjectId blob) throws IOException {
		ObjectLoader loader = repository.open(blob
		return new RawText(loader.getCachedBytes(Integer.MAX_VALUE));
	}

	private String wasPathRenamed(RevCommit rev) throws IOException {
		TreeWalk tw = new TreeWalk(walk.getObjectReader());
		try {
			tw.setFilter(TreeFilter.ANY_DIFF);
			tw.reset(rev.getTree()
			RenameDetector detector = new RenameDetector(repository);
			detector.addAll(DiffEntry.scan(tw));
			for (DiffEntry ent : detector.compute()) {
				if (isRename(ent) && ent.getNewPath().equals(srcPath))
					return ent.getOldPath();
			}
			return null;
		} finally {
			tw.release();
		}
	}

	private boolean isRename(DiffEntry ent) {
		return ent.getChangeType() == ChangeType.RENAME
				|| ent.getChangeType() == ChangeType.COPY;
	}
}
