
package org.eclipse.jgit.blame;

import static org.eclipse.jgit.lib.Constants.OBJ_BLOB;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

import org.eclipse.jgit.JGitText;
import org.eclipse.jgit.blame.Candidate.BlobCandidate;
import org.eclipse.jgit.blame.Candidate.ReverseCandidate;
import org.eclipse.jgit.blame.ReverseWalk.ReverseCommit;
import org.eclipse.jgit.diff.DiffAlgorithm;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffEntry.ChangeType;
import org.eclipse.jgit.diff.EditList;
import org.eclipse.jgit.diff.HistogramDiff;
import org.eclipse.jgit.diff.RawText;
import org.eclipse.jgit.diff.RawTextComparator;
import org.eclipse.jgit.diff.RenameDetector;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.MutableObjectId;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
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

	private final MutableObjectId idBuf;

	private RevWalk revPool;

	private RevFlag SEEN;

	private ObjectReader reader;

	private TreeWalk treeWalk;

	private DiffAlgorithm diffAlgorithm = new HistogramDiff();

	private RawTextComparator textComparator = RawTextComparator.DEFAULT;

	private RenameDetector renameDetector;

	private Candidate queue;

	private int remaining;

	private Candidate currentSource;

	public BlameGenerator(Repository repository
		this.repository = repository;
		this.resultPath = PathFilter.create(path);

		idBuf = new MutableObjectId();
		setFollowFileRenames(true);
		initRevPool(false);

		remaining = -1;
	}

	private void initRevPool(boolean reverse) {
		if (queue != null)
			throw new IllegalStateException();

		if (revPool != null)
			revPool.release();

		if (reverse)
			revPool = new ReverseWalk(getRepository());
		else
			revPool = new RevWalk(getRepository());

		revPool.setRetainBody(true);
		SEEN = revPool.newFlag("SEEN");
		reader = revPool.getObjectReader();
		treeWalk = new TreeWalk(reader);
	}

	public Repository getRepository() {
		return repository;
	}

	public String getResultPath() {
		return resultPath.getPath();
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

	public BlameGenerator push(String description
			throws IOException {
		return push(description
	}

	public BlameGenerator push(String description
			throws IOException {
		if (description == null)
			description = JGitText.get().blameNotCommittedYet;
		BlobCandidate c = new BlobCandidate(description
		c.sourceText = contents;
		c.regionList = new Region(0
		remaining = contents.size();
		push(c);
		return this;
	}

	public BlameGenerator push(String description
			throws IOException {
		ObjectLoader ldr = reader.open(id);
		if (ldr.getType() == OBJ_BLOB) {
			if (description == null)
				description = JGitText.get().blameNotCommittedYet;
			BlobCandidate c = new BlobCandidate(description
			c.sourceBlob = id.toObjectId();
			c.sourceText = new RawText(ldr.getCachedBytes(Integer.MAX_VALUE));
			c.regionList = new Region(0
			remaining = c.sourceText.size();
			push(c);
			return this;
		}

		RevCommit commit = revPool.parseCommit(id);
		if (!find(commit
			return this;

		Candidate c = new Candidate(commit
		c.sourceBlob = idBuf.toObjectId();
		c.loadText(reader);
		c.regionList = new Region(0
		remaining = c.sourceText.size();
		push(c);
		return this;
	}

	public BlameGenerator reverse(AnyObjectId start
			throws IOException {
		return reverse(start
	}

	public BlameGenerator reverse(AnyObjectId start
			Collection<? extends ObjectId> end) throws IOException {
		initRevPool(true);

		ReverseCommit result = (ReverseCommit) revPool.parseCommit(start);
		if (!find(result
			return this;

		revPool.markUninteresting(result);
		for (ObjectId id : end)
			revPool.markStart(revPool.parseCommit(id));

		while (revPool.next() != null) {
		}

		ReverseCandidate c = new ReverseCandidate(result
		c.sourceBlob = idBuf.toObjectId();
		c.loadText(reader);
		c.regionList = new Region(0
		remaining = c.sourceText.size();
		push(c);
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
		if (currentSource != null) {
			Region r = currentSource.regionList;
			Region n = r.next;
			remaining -= r.length;
			if (n != null) {
				currentSource.regionList = n;
				return true;
			}

			if (currentSource.queueNext != null)
				return result(currentSource.queueNext);

			currentSource = null;
		}

		if (remaining == 0)
			return done();

		for (;;) {
			Candidate n = pop();
			if (n == null)
				return done();

			int pCnt = n.getParentCount();
			if (pCnt == 1) {
				if (processOne(n))
					return true;

			} else if (1 < pCnt) {
				if (processMerge(n))
					return true;

			} else if (n instanceof ReverseCandidate) {

				return result(n);
			}
		}
	}

	private boolean done() {
		release();
		return false;
	}

	private boolean result(Candidate n) throws IOException {
		if (n.sourceCommit != null)
			revPool.parseBody(n.sourceCommit);
		currentSource = n;
		return true;
	}

	private boolean reverseResult(Candidate parent
			throws IOException {
		Candidate res = parent.copy(parent.sourceCommit);
		res.regionList = source.regionList;
		return result(res);
	}

	private Candidate pop() {
		Candidate n = queue;
		if (n != null) {
			queue = n.queueNext;
			n.queueNext = null;
		}
		return n;
	}

	private void push(BlobCandidate toInsert) {
		Candidate c = queue;
		if (c != null) {
			c.regionList = null;
			toInsert.parent = c;
		}
		queue = toInsert;
	}

	private void push(Candidate toInsert) {
		toInsert.add(SEEN);

		int time = toInsert.getTime();
		Candidate n = queue;
		if (n == null || time >= n.getTime()) {
			toInsert.queueNext = n;
			queue = toInsert;
			return;
		}

		for (Candidate p = n;; p = n) {
			n = p.queueNext;
			if (n == null || time >= n.getTime()) {
				toInsert.queueNext = n;
				p.queueNext = toInsert;
				return;
			}
		}
	}

	private boolean processOne(Candidate n) throws IOException {
		RevCommit parent = n.getParent(0);
		if (parent == null)
			return split(n.getNextCandidate(0)
		if (parent.has(SEEN))
			return false;
		revPool.parseHeaders(parent);

		if (find(parent
			if (idBuf.equals(n.sourceBlob)) {
				n.sourceCommit = parent;
				push(n);
				return false;
			}

			Candidate next = n.create(parent
			next.sourceBlob = idBuf.toObjectId();
			next.loadText(reader);
			return split(next
		}

		if (n.sourceCommit == null)
			return result(n);

		DiffEntry r = findRename(parent
		if (r == null)
			return result(n);

		if (0 == r.getOldId().prefixCompare(n.sourceBlob)) {
			n.sourceCommit = parent;
			n.sourcePath = PathFilter.create(r.getOldPath());
			push(n);
			return false;
		}

		Candidate next = n.create(parent
		next.sourceBlob = r.getOldId().toObjectId();
		next.renameScore = r.getScore();
		next.loadText(reader);
		return split(next
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
		if (source.regionList != null) {
			if (source instanceof ReverseCandidate)
				return reverseResult(parent
			return result(source);
		}
		return false;
	}

	private boolean processMerge(Candidate n) throws IOException {
		int pCnt = n.getParentCount();

		for (int pIdx = 0; pIdx < pCnt; pIdx++) {
			RevCommit parent = n.getParent(pIdx);
			if (parent.has(SEEN))
				continue;
			revPool.parseHeaders(parent);
		}

		ObjectId[] ids = null;
		for (int pIdx = 0; pIdx < pCnt; pIdx++) {
			RevCommit parent = n.getParent(pIdx);
			if (parent.has(SEEN))
				continue;
			if (!find(parent
				continue;
			if (!(n instanceof ReverseCandidate) && idBuf.equals(n.sourceBlob)) {
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
				RevCommit parent = n.getParent(pIdx);
				if (parent.has(SEEN))
					continue;
				if (ids != null && ids[pIdx] != null)
					continue;

				DiffEntry r = findRename(parent
				if (r == null)
					continue;

				if (n instanceof ReverseCandidate) {
					if (ids == null)
						ids = new ObjectId[pCnt];
					ids[pCnt] = r.getOldId().toObjectId();
				} else if (0 == r.getOldId().prefixCompare(n.sourceBlob)) {
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
			RevCommit parent = n.getParent(pIdx);
			if (parent.has(SEEN))
				continue;

			Candidate p;
			if (renames != null && renames[pIdx] != null) {
				p = n.create(parent
						PathFilter.create(renames[pIdx].getOldPath()));
				p.renameScore = renames[pIdx].getScore();
				p.sourceBlob = renames[pIdx].getOldId().toObjectId();
			} else if (ids != null && ids[pIdx] != null) {
				p = n.create(parent
				p.sourceBlob = ids[pIdx];
			} else {
				continue;
			}

			EditList editList;
			if (n instanceof ReverseCandidate
					&& p.sourceBlob.equals(n.sourceBlob)) {
				p.sourceText = n.sourceText;
				editList = new EditList(0);
			} else {
				p.loadText(reader);
				editList = diffAlgorithm.diff(textComparator
						p.sourceText
			}

			if (editList.isEmpty()) {
				if (n instanceof ReverseCandidate) {
					parents[pIdx] = p;
					continue;
				}

				p.regionList = n.regionList;
				push(p);
				return false;
			}

			p.takeBlame(editList

			if (p.regionList != null) {
				if (n instanceof ReverseCandidate) {
					Region r = p.regionList;
					p.regionList = n.regionList;
					n.regionList = r;
				}

				parents[pIdx] = p;
			}
		}

		if (n instanceof ReverseCandidate) {
			Candidate resultHead = null;
			Candidate resultTail = null;

			for (int pIdx = 0; pIdx < pCnt; pIdx++) {
				Candidate p = parents[pIdx];
				if (p == null)
					continue;

				if (p.regionList != null) {
					Candidate r = p.copy(p.sourceCommit);
					if (resultTail != null) {
						resultTail.queueNext = r;
						resultTail = r;
					} else {
						resultHead = r;
						resultTail = r;
					}
				}

				if (n.regionList != null) {
					p.regionList = n.regionList.deepCopy();
					push(p);
				}
			}

			if (resultHead != null)
				return result(resultHead);
			return false;
		}

		for (int pIdx = 0; pIdx < pCnt; pIdx++) {
			if (parents[pIdx] != null)
				push(parents[pIdx]);
		}

		if (n.regionList != null)
			return result(n);
		return false;
	}

	public RevCommit getSourceCommit() {
		return currentSource.sourceCommit;
	}

	public PersonIdent getSourceAuthor() {
		return currentSource.getAuthor();
	}

	public PersonIdent getSourceCommitter() {
		RevCommit c = getSourceCommit();
		return c != null ? c.getCommitterIdent() : null;
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

	public RawText getResultContents() throws IOException {
		return queue != null ? queue.sourceText : null;
	}

	public void release() {
		revPool.release();
		queue = null;
		currentSource = null;
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
