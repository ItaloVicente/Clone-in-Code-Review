
package org.eclipse.jgit.blame;

import static org.eclipse.jgit.lib.Constants.OBJ_BLOB;
import static org.eclipse.jgit.lib.FileMode.TYPE_FILE;
import static org.eclipse.jgit.lib.FileMode.TYPE_MASK;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

import org.eclipse.jgit.annotations.Nullable;
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
import org.eclipse.jgit.internal.JGitText;
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

public class BlameGenerator implements AutoCloseable {
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

	private Candidate outCandidate;
	private Region outRegion;

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
			revPool.close();

		if (reverse)
			revPool = new ReverseWalk(getRepository());
		else
			revPool = new RevWalk(getRepository());

		reader = revPool.getObjectReader();
		treeWalk = new TreeWalk(reader);
		treeWalk.setRecursive(true);
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

	@Nullable
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
		BlobCandidate c = new BlobCandidate(getRepository()
				resultPath);
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
			BlobCandidate c = new BlobCandidate(getRepository()
					resultPath);
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

		Candidate c = new Candidate(getRepository()
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

		ReverseCandidate c = new ReverseCandidate(getRepository()
				resultPath);
		c.sourceBlob = idBuf.toObjectId();
		c.loadText(reader);
		c.regionList = new Region(0
		remaining = c.sourceText.size();
		push(c);
		return this;
	}

	public RevFlag newFlag(String name) {
		return revPool.newFlag(name);
	}

	public BlameResult computeBlameResult() throws IOException {
		try {
			BlameResult r = BlameResult.create(this);
			if (r != null)
				r.computeAll();
			return r;
		} finally {
			close();
		}
	}

	public boolean next() throws IOException {
		if (outRegion != null) {
			Region r = outRegion;
			remaining -= r.length;
			if (r.next != null) {
				outRegion = r.next;
				return true;
			}

			if (outCandidate.queueNext != null)
				return result(outCandidate.queueNext);

			outCandidate = null;
			outRegion = null;
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
		close();
		return false;
	}

	private boolean result(Candidate n) throws IOException {
		n.beginResult(revPool);
		outCandidate = n;
		outRegion = n.regionList;
		return outRegion != null;
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
		if (toInsert.has(SEEN)) {
			for (Candidate p = queue; p != null; p = p.queueNext) {
				if (p.canMergeRegions(toInsert)) {
					p.mergeRegions(toInsert);
					return;
				}
			}
		}
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
		revPool.parseHeaders(parent);

		if (find(parent
			if (idBuf.equals(n.sourceBlob))
				return blameEntireRegionOnParent(n
			return splitBlameWithParent(n
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

		Candidate next = n.create(getRepository()
				PathFilter.create(r.getOldPath()));
		next.sourceBlob = r.getOldId().toObjectId();
		next.renameScore = r.getScore();
		next.loadText(reader);
		return split(next
	}

	private boolean blameEntireRegionOnParent(Candidate n
		n.sourceCommit = parent;
		push(n);
		return false;
	}

	private boolean splitBlameWithParent(Candidate n
			throws IOException {
		Candidate next = n.create(getRepository()
		next.sourceBlob = idBuf.toObjectId();
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

		ObjectId[] ids = null;
		for (int pIdx = 0; pIdx < pCnt; pIdx++) {
			RevCommit parent = n.getParent(pIdx);
			revPool.parseHeaders(parent);
			if (!find(parent
				continue;
			if (!(n instanceof ReverseCandidate) && idBuf.equals(n.sourceBlob))
				return blameEntireRegionOnParent(n
			if (ids == null)
				ids = new ObjectId[pCnt];
			ids[pIdx] = idBuf.toObjectId();
		}

		DiffEntry[] renames = null;
		if (renameDetector != null) {
			renames = new DiffEntry[pCnt];
			for (int pIdx = 0; pIdx < pCnt; pIdx++) {
				RevCommit parent = n.getParent(pIdx);
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
					n.sourcePath = PathFilter.create(r.getOldPath());
					return blameEntireRegionOnParent(n
				}

				renames[pIdx] = r;
			}
		}

		Candidate[] parents = new Candidate[pCnt];
		for (int pIdx = 0; pIdx < pCnt; pIdx++) {
			RevCommit parent = n.getParent(pIdx);

			Candidate p;
			if (renames != null && renames[pIdx] != null) {
				p = n.create(getRepository()
						PathFilter.create(renames[pIdx].getOldPath()));
				p.renameScore = renames[pIdx].getScore();
				p.sourceBlob = renames[pIdx].getOldId().toObjectId();
			} else if (ids != null && ids[pIdx] != null) {
				p = n.create(getRepository()
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
				n.regionList = null;
				parents[pIdx] = p;
				break;
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
		return outCandidate.sourceCommit;
	}

	public PersonIdent getSourceAuthor() {
		return outCandidate.getAuthor();
	}

	public PersonIdent getSourceCommitter() {
		RevCommit c = getSourceCommit();
		return c != null ? c.getCommitterIdent() : null;
	}

	public String getSourcePath() {
		return outCandidate.sourcePath.getPath();
	}

	public int getRenameScore() {
		return outCandidate.renameScore;
	}

	public int getSourceStart() {
		return outRegion.sourceStart;
	}

	public int getSourceEnd() {
		Region r = outRegion;
		return r.sourceStart + r.length;
	}

	public int getResultStart() {
		return outRegion.resultStart;
	}

	public int getResultEnd() {
		Region r = outRegion;
		return r.resultStart + r.length;
	}

	public int getRegionLength() {
		return outRegion.length;
	}

	public RawText getSourceContents() {
		return outCandidate.sourceText;
	}

	public RawText getResultContents() throws IOException {
		return queue != null ? queue.sourceText : null;
	}

	@Override
	public void close() {
		revPool.close();
		queue = null;
		outCandidate = null;
		outRegion = null;
	}

	private boolean find(RevCommit commit
		treeWalk.setFilter(path);
		treeWalk.reset(commit.getTree());
		if (treeWalk.next() && isFile(treeWalk.getRawMode(0))) {
			treeWalk.getObjectId(idBuf
			return true;
		}
		return false;
	}

	private static final boolean isFile(int rawMode) {
		return (rawMode & TYPE_MASK) == TYPE_FILE;
	}

	private DiffEntry findRename(RevCommit parent
			PathFilter path) throws IOException {
		if (renameDetector == null)
			return null;

		treeWalk.setFilter(TreeFilter.ANY_DIFF);
		treeWalk.reset(parent.getTree()
		renameDetector.reset();
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
