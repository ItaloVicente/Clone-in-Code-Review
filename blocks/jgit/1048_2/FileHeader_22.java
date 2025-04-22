
package org.eclipse.jgit.diff;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jgit.JGitText;
import org.eclipse.jgit.diff.DiffEntry.ChangeType;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Repository;

class SimilarityRenameDetector {
	private static final int BITS_PER_INDEX = 28;

	private static final int INDEX_MASK = (1 << BITS_PER_INDEX) - 1;

	private static final int SCORE_SHIFT = 2 * BITS_PER_INDEX;

	private final Repository repo;

	private List<DiffEntry> srcs;

	private List<DiffEntry> dsts;

	private long[] matrix;

	private int renameScore = 60;

	private List<DiffEntry> out;

	SimilarityRenameDetector(Repository repo
			List<DiffEntry> dsts) {
		this.repo = repo;
		this.srcs = srcs;
		this.dsts = dsts;
	}

	void setRenameScore(int score) {
		renameScore = score;
	}

	void compute(ProgressMonitor pm) throws IOException {
		if (pm == null)
			pm = NullProgressMonitor.INSTANCE;

		pm.beginTask(JGitText.get().renamesFindingByContent
				2 * srcs.size() * dsts.size());

		int mNext = buildMatrix(pm);
		out = new ArrayList<DiffEntry>(Math.min(mNext

		for (--mNext; mNext >= 0; mNext--) {
			long ent = matrix[mNext];
			int sIdx = srcFile(ent);
			int dIdx = dstFile(ent);
			DiffEntry s = srcs.get(sIdx);
			DiffEntry d = dsts.get(dIdx);

			if (d == null) {
				pm.update(1);
			}

			ChangeType type;
			if (s.changeType == ChangeType.DELETE) {
				s.changeType = ChangeType.RENAME;
				type = ChangeType.RENAME;
			} else {
				type = ChangeType.COPY;
			}

			out.add(DiffEntry.pair(type
			dsts.set(dIdx
			pm.update(1);
		}

		srcs = compactSrcList(srcs);
		dsts = compactDstList(dsts);
		pm.endTask();
	}

	List<DiffEntry> getMatches() {
		return out;
	}

	List<DiffEntry> getLeftOverSources() {
		return srcs;
	}

	List<DiffEntry> getLeftOverDestinations() {
		return dsts;
	}

	private static List<DiffEntry> compactSrcList(List<DiffEntry> in) {
		ArrayList<DiffEntry> r = new ArrayList<DiffEntry>(in.size());
		for (DiffEntry e : in) {
			if (e.changeType == ChangeType.DELETE)
				r.add(e);
		}
		return r;
	}

	private static List<DiffEntry> compactDstList(List<DiffEntry> in) {
		ArrayList<DiffEntry> r = new ArrayList<DiffEntry>(in.size());
		for (DiffEntry e : in) {
			if (e != null)
				r.add(e);
		}
		return r;
	}

	private int buildMatrix(ProgressMonitor pm) throws IOException {
		matrix = new long[srcs.size() * dsts.size()];

		int mNext = 0;
		for (int srcIdx = 0; srcIdx < srcs.size(); srcIdx++) {
			DiffEntry srcEnt = srcs.get(srcIdx);
			if (!isFile(srcEnt.oldMode)) {
				pm.update(dsts.size());
				continue;
			}

			SimilarityIndex s = hash(srcEnt.oldId.toObjectId());
			for (int dstIdx = 0; dstIdx < dsts.size(); dstIdx++) {
				DiffEntry dstEnt = dsts.get(dstIdx);

				if (!isFile(dstEnt.newMode)) {
					pm.update(1);
					continue;
				}

				if (!RenameDetector.sameType(srcEnt.oldMode
					pm.update(1);
					continue;
				}

				SimilarityIndex d = hash(dstEnt.newId.toObjectId());
				int score = s.score(d);

				if (score < renameScore) {
					pm.update(1);
					continue;
				}

				matrix[mNext++] = encode(score
				pm.update(1);
			}
		}

		Arrays.sort(matrix
		return mNext;
	}

	private SimilarityIndex hash(ObjectId objectId) throws IOException {
		SimilarityIndex r = new SimilarityIndex();
		r.hash(repo.openObject(objectId));
		r.sort();
		return r;
	}

	private static int score(long value) {
		return (int) (value >>> SCORE_SHIFT);
	}

	private static int srcFile(long value) {
		return decodeFile(((int) (value >>> BITS_PER_INDEX)) & INDEX_MASK);
	}

	private static int dstFile(long value) {
		return decodeFile(((int) value) & INDEX_MASK);
	}

	private static long encode(int score
				| encodeFile(dstIdx);
	}

	private static long encodeFile(int idx) {
		return INDEX_MASK - idx;
	}

	private static int decodeFile(int v) {
		return INDEX_MASK - v;
	}

	private static boolean isFile(FileMode mode) {
		return (mode.getBits() & FileMode.TYPE_MASK) == FileMode.TYPE_FILE;
	}
}
