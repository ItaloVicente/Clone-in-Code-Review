
package org.eclipse.jgit.blame;

import static org.eclipse.jgit.lib.Constants.OBJ_BLOB;

import java.io.IOException;

import org.eclipse.jgit.JGitText;
import org.eclipse.jgit.diff.DiffAlgorithm;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffEntry.ChangeType;
import org.eclipse.jgit.diff.EditList;
import org.eclipse.jgit.diff.HistogramDiff;
import org.eclipse.jgit.diff.RawText;
import org.eclipse.jgit.diff.RawTextComparator;
import org.eclipse.jgit.diff.RenameDetector;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.MutableObjectId;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevFlag;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilter;
import org.eclipse.jgit.treewalk.filter.TreeFilter;

public class BlameGenerator {
	private final Repository repository;

	private final PathFilter resultPath;

	private final RevWalk revPool;

	private final RevFlag SEEN;

	private final ObjectReader reader;

	private final TreeWalk treeWalk;

	private final MutableObjectId idBuf;

	private DiffAlgorithm diffAlgorithm = new HistogramDiff();

	private RawTextComparator textComparator = RawTextComparator.DEFAULT;

	private RenameDetector renameDetector;

	private AnyObjectId start;

	private boolean notStarted;

	private Candidate queue;

	private int remaining;

	private Candidate currentSource;

	public BlameGenerator(Repository repository
		this.repository = repository;
		this.resultPath = PathFilter.create(path);

		revPool = new RevWalk(repository);
		revPool.setRetainBody(true);
		SEEN = revPool.newFlag("SEEN");
		reader = revPool.getObjectReader();
		treeWalk = new TreeWalk(reader);
		idBuf = new MutableObjectId();
		setFollowFileRenames(true);

		remaining = -1;
		notStarted = true;
	}

	public Repository getRepository() {
		return repository;
	}

	public String getResultPath() {
		return resultPath.getPath();
	}

	public BlameGenerator setStartRevision(ObjectId commitId) {
		start = commitId;
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

	public BlameGenerator setFollowFileRenames(boolean follow) {
		if (follow)
			renameDetector = new RenameDetector(getRepository());
		else
			renameDetector = null;
		return this;
	}

	public RenameDetector getRenameDetector() {
		return renameDetector;
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
		if (currentSource != null) {
			Region r = currentSource.regionList;
			Region n = r.next;
			remaining -= r.length;
			if (n != null) {
				currentSource.regionList = n;
				return true;
			}
			currentSource = null;
		}

		if (remaining == 0)
			return done();

		for (;;) {
			Candidate n = pop();
			if (n == null) {
				if (notStarted && start()) {
					notStarted = false;
					continue;
				}
				return done();
			}

			RevCommit source = n.sourceCommit;
			int pCnt = source.getParentCount();
			if (pCnt == 1) {
				if (processOne(n))
					return true;

			} else if (1 < pCnt) {
				if (processMerge(n))
					return true;

				return result(n);
			}
		}
	}

	private boolean done() {
		release();
		return false;
	}

	private boolean result(Candidate n) throws IOException {
		revPool.parseBody(n.sourceCommit);
		currentSource = n;
		return true;
	}

	private Candidate pop() {
		Candidate n = queue;
		if (n != null)
			queue = n.queueNext;
		return n;
	}

	private void push(Candidate toInsert) {
		toInsert.sourceCommit.add(SEEN);

		int time = toInsert.sourceCommit.getCommitTime();
		Candidate n = queue;
		if (n == null || time >= n.sourceCommit.getCommitTime()) {
			toInsert.queueNext = n;
			queue = toInsert;
			return;
		}

		for (Candidate p = n;; p = n) {
			n = p.queueNext;
			if (n == null || time >= n.sourceCommit.getCommitTime()) {
				toInsert.queueNext = n;
				p.queueNext = toInsert;
				return;
			}
		}
	}

	private boolean processOne(Candidate n) throws IOException {
		RevCommit source = n.sourceCommit;
		RevCommit parent = source.getParent(0);
		if (parent.has(SEEN))
			return false;
		revPool.parseHeaders(parent);

		if (find(parent
			if (idBuf.equals(n.sourceBlob)) {
				n.sourceCommit = parent;
				push(n);
				return false;
			}

			Candidate next = new Candidate(parent
			next.sourceBlob = idBuf.toObjectId();
			next.loadText(reader);
			return split(next
		}

		DiffEntry r = findRename(parent
		if (r == null)
			return result(n);

		if (0 == r.getOldId().prefixCompare(n.sourceBlob)) {
			n.sourceCommit = parent;
			n.sourcePath = PathFilter.create(r.getOldPath());
			push(n);
			return false;
		}

		Candidate next = new Candidate(parent
		next.sourceBlob = r.getOldId().toObjectId();
		next.renameScore = r.getScore();
		next.loadText(reader);
		return split(next
	}

	private boolean processMerge(Candidate n) throws IOException {
		RevCommit source = n.sourceCommit;
		int pCnt = source.getParentCount();

		for (int pIdx = 0; pIdx < pCnt; pIdx++) {
			RevCommit parent = source.getParent(pIdx);
			if (parent.has(SEEN))
				continue;
			revPool.parseHeaders(parent);
		}

		ObjectId[] ids = null;
		for (int pIdx = 0; pIdx < pCnt; pIdx++) {
			RevCommit parent = source.getParent(pIdx);
			if (parent.has(SEEN))
				continue;
			if (!find(parent
				continue;
			if (idBuf.equals(n.sourceBlob)) {
				n.sourceCommit = parent;
				push(n);
				return false;
			}
			if (ids == null)
				ids = new ObjectId[pCnt];
			ids[pIdx] = idBuf.toObjectId();
		}

		DiffEntry[] renames = null;
		if (renameDetector != null) {
			renames = new DiffEntry[pCnt];
			for (int pIdx = 0; pIdx < pCnt; pIdx++) {
				RevCommit parent = source.getParent(pIdx);
				if (parent.has(SEEN))
					continue;
				if (ids != null && ids[pIdx] != null)
					continue;

				DiffEntry r = findRename(parent
				if (r == null)
					continue;

				if (0 == r.getOldId().prefixCompare(n.sourceBlob)) {
					n.sourceCommit = parent;
					n.sourcePath = PathFilter.create(r.getOldPath());
					push(n);
					return false;
				}

				renames[pIdx] = r;
			}
		}

		Candidate[] parents = new Candidate[pCnt];
		for (int pIdx = 0; pIdx < pCnt; pIdx++) {
			RevCommit parent = source.getParent(pIdx);
			if (parent.has(SEEN))
				continue;

			Candidate p;
			if (renames != null && renames[pIdx] != null) {
				p = new Candidate(parent
						PathFilter.create(renames[pIdx].getOldPath()));
				p.renameScore = renames[pIdx].getScore();
				p.sourceBlob = renames[pIdx].getOldId().toObjectId();
			} else if (ids != null && ids[pIdx] != null) {
				p = new Candidate(parent
				p.sourceBlob = ids[pIdx];
			} else {
				continue;
			}
			p.loadText(reader);

			EditList editList = diffAlgorithm.diff(textComparator
					p.sourceText
			if (editList.isEmpty()) {
				p.regionList = n.regionList;
				push(p);
				return false;
			}

			p.takeBlame(editList

			if (p.regionList != null)
				parents[pIdx] = p;
		}

		for (int pIdx = 0; pIdx < pCnt; pIdx++) {
			if (parents[pIdx] != null)
				push(parents[pIdx]);
		}

		if (n.regionList != null)
			return result(n);
		return false;
	}

	private boolean split(Candidate parent
			throws IOException {
		EditList editList = diffAlgorithm.diff(textComparator
				parent.sourceText
		if (editList.isEmpty()) {
			parent.regionList = source.regionList;
			push(parent);
			return false;
		}

		parent.takeBlame(editList
		if (parent.regionList != null)
			push(parent);
		if (source.regionList != null)
			return result(source);
		return false;
	}

	public RevCommit getSourceCommit() {
		return currentSource.sourceCommit;
	}

	public PersonIdent getSourceAuthor() {
		return getSourceCommit().getAuthorIdent();
	}

	public PersonIdent getSourceCommitter() {
		return getSourceCommit().getCommitterIdent();
	}

	public String getSourcePath() {
		return currentSource.sourcePath.getPath();
	}

	public int getRenameScore() {
		return currentSource.renameScore;
	}

	public int getSourceStart() {
		return currentSource.regionList.sourceStart;
	}

	public int getSourceEnd() {
		Region r = currentSource.regionList;
		return r.sourceStart + r.length;
	}

	public int getResultStart() {
		return currentSource.regionList.resultStart;
	}

	public int getResultEnd() {
		Region r = currentSource.regionList;
		return r.resultStart + r.length;
	}

	public int getRegionLength() {
		return currentSource.regionList.length;
	}

	public RawText getSourceContents() {
		return currentSource.sourceText;
	}

	public RevCommit getResultCommit() throws IOException {
		if (notStarted) {
			if (start())
				notStarted = false;
			else
				return null;
		}

		Candidate c = queue;
		if (c.queueNext != null || remaining != c.sourceText.size())
			throw new IllegalStateException(JGitText.get().blameHasAlreadyBeenStarted);
		return c.sourceCommit;
	}

	public RawText getResultContents() throws IOException {
		if (notStarted) {
			if (start())
				notStarted = false;
			else
				return null;
		}

		Candidate c = queue;
		if (c.queueNext != null || remaining != c.sourceText.size())
			throw new IllegalStateException(JGitText.get().blameHasAlreadyBeenStarted);
		return c.sourceText;
	}

	private boolean start() throws IOException {
		RevCommit commit;
		if (start != null) {
			commit = revPool.parseCommit(start);
		} else {
			AnyObjectId head = repository.resolve(Constants.HEAD);
			if (head == null)
				return false;
			commit = revPool.parseCommit(head);
		}

		if (!find(commit
			return false;

		Candidate toInsert = new Candidate(commit
		toInsert.sourceBlob = idBuf.toObjectId();
		toInsert.loadText(reader);
		toInsert.regionList = new Region(0
		remaining = toInsert.sourceText.size();
		queue = toInsert;
		currentSource = null;
		commit.add(SEEN);
		return true;
	}

	public BlameGenerator release() {
		revPool.release();
		queue = null;
		currentSource = null;
		return this;
	}

	private boolean find(RevCommit commit
		treeWalk.setFilter(path);
		treeWalk.reset(commit.getTree());
		while (treeWalk.next()) {
			if (path.isDone(treeWalk)) {
				if (treeWalk.getFileMode(0).getObjectType() != OBJ_BLOB)
					return false;
				treeWalk.getObjectId(idBuf
				return true;
			}

			if (treeWalk.isSubtree())
				treeWalk.enterSubtree();
		}
		return false;
	}

	private DiffEntry findRename(RevCommit parent
			PathFilter path) throws IOException {
		if (renameDetector == null)
			return null;

		treeWalk.setFilter(TreeFilter.ANY_DIFF);
		treeWalk.reset(parent.getTree()
		renameDetector.addAll(DiffEntry.scan(treeWalk));
		for (DiffEntry ent : renameDetector.compute()) {
			if (isRename(ent) && ent.getNewPath().equals(path.getPath()))
				return ent;
		}
		return null;
	}

	private static boolean isRename(DiffEntry ent) {
		return ent.getChangeType() == ChangeType.RENAME
				|| ent.getChangeType() == ChangeType.COPY;
	}
}
