
package org.eclipse.jgit.merge;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jgit.diff.DiffAlgorithm;
import org.eclipse.jgit.diff.Edit;
import org.eclipse.jgit.diff.EditList;
import org.eclipse.jgit.diff.HistogramDiff;
import org.eclipse.jgit.diff.Sequence;
import org.eclipse.jgit.diff.SequenceComparator;
import org.eclipse.jgit.merge.MergeChunk.ConflictState;

public final class MergeAlgorithm {
	private final DiffAlgorithm diffAlg;

	public MergeAlgorithm() {
		this(new HistogramDiff());
	}

	public MergeAlgorithm(DiffAlgorithm diff) {
		this.diffAlg = diff;
	}

	private final static Edit END_EDIT = new Edit(Integer.MAX_VALUE
			Integer.MAX_VALUE);

	public <S extends Sequence> MergeResult<S> merge(
			SequenceComparator<S> cmp
		List<S> sequences = new ArrayList<>(3);
		sequences.add(base);
		sequences.add(ours);
		sequences.add(theirs);
		MergeResult<S> result = new MergeResult<>(sequences);

		if (ours.size() == 0) {
			if (theirs.size() != 0) {
				EditList theirsEdits = diffAlg.diff(cmp
				if (!theirsEdits.isEmpty()) {
					result.add(1
					result.add(2
							ConflictState.NEXT_CONFLICTING_RANGE);
				} else
					result.add(1
			} else
				result.add(1
			return result;
		} else if (theirs.size() == 0) {
			EditList oursEdits = diffAlg.diff(cmp
			if (!oursEdits.isEmpty()) {
				result.add(1
						ConflictState.FIRST_CONFLICTING_RANGE);
				result.add(2
			} else
				result.add(2
			return result;
		}

		EditList oursEdits = diffAlg.diff(cmp
		Iterator<Edit> baseToOurs = oursEdits.iterator();
		EditList theirsEdits = diffAlg.diff(cmp
		Iterator<Edit> baseToTheirs = theirsEdits.iterator();
		Edit oursEdit = nextEdit(baseToOurs);
		Edit theirsEdit = nextEdit(baseToTheirs);

		while (theirsEdit != END_EDIT || oursEdit != END_EDIT) {
			if (oursEdit.getEndA() < theirsEdit.getBeginA()) {
				if (current != oursEdit.getBeginA()) {
					result.add(0
							ConflictState.NO_CONFLICT);
				}
				result.add(1
						ConflictState.NO_CONFLICT);
				current = oursEdit.getEndA();
				oursEdit = nextEdit(baseToOurs);
			} else if (theirsEdit.getEndA() < oursEdit.getBeginA()) {
				if (current != theirsEdit.getBeginA()) {
					result.add(0
							ConflictState.NO_CONFLICT);
				}
				result.add(2
						ConflictState.NO_CONFLICT);
				current = theirsEdit.getEndA();
				theirsEdit = nextEdit(baseToTheirs);
			} else {

				if (oursEdit.getBeginA() != current
						&& theirsEdit.getBeginA() != current) {
					result.add(0
							theirsEdit.getBeginA())
				}

				int oursBeginB = oursEdit.getBeginB();
				int theirsBeginB = theirsEdit.getBeginB();
				if (oursEdit.getBeginA() < theirsEdit.getBeginA()) {
					theirsBeginB -= theirsEdit.getBeginA()
							- oursEdit.getBeginA();
				} else {
					oursBeginB -= oursEdit.getBeginA() - theirsEdit.getBeginA();
				}

				Edit nextOursEdit = nextEdit(baseToOurs);
				Edit nextTheirsEdit = nextEdit(baseToTheirs);
				for (;;) {
					if (oursEdit.getEndA() >= nextTheirsEdit.getBeginA()) {
						theirsEdit = nextTheirsEdit;
						nextTheirsEdit = nextEdit(baseToTheirs);
					} else if (theirsEdit.getEndA() >= nextOursEdit.getBeginA()) {
						oursEdit = nextOursEdit;
						nextOursEdit = nextEdit(baseToOurs);
					} else {
						break;
					}
				}

				int oursEndB = oursEdit.getEndB();
				int theirsEndB = theirsEdit.getEndB();
				if (oursEdit.getEndA() < theirsEdit.getEndA()) {
					oursEndB += theirsEdit.getEndA() - oursEdit.getEndA();
				} else {
					theirsEndB += oursEdit.getEndA() - theirsEdit.getEndA();
				}


				int minBSize = oursEndB - oursBeginB;
				int BSizeDelta = minBSize - (theirsEndB - theirsBeginB);
				if (BSizeDelta > 0)
					minBSize -= BSizeDelta;

				int commonPrefix = 0;
				while (commonPrefix < minBSize
						&& cmp.equals(ours
								theirsBeginB + commonPrefix))
					commonPrefix++;
				minBSize -= commonPrefix;
				int commonSuffix = 0;
				while (commonSuffix < minBSize
						&& cmp.equals(ours
								theirsEndB - commonSuffix - 1))
					commonSuffix++;
				minBSize -= commonSuffix;

				if (commonPrefix > 0)
					result.add(1
							ConflictState.NO_CONFLICT);

				if (minBSize > 0 || BSizeDelta != 0) {
					result.add(1
							- commonSuffix
							ConflictState.FIRST_CONFLICTING_RANGE);
					result.add(2
							- commonSuffix
							ConflictState.NEXT_CONFLICTING_RANGE);
				}

				if (commonSuffix > 0)
					result.add(1
							ConflictState.NO_CONFLICT);

				current = Math.max(oursEdit.getEndA()
				oursEdit = nextOursEdit;
				theirsEdit = nextTheirsEdit;
			}
		}
		if (current < base.size()) {
			result.add(0
		}
		return result;
	}

	private static Edit nextEdit(Iterator<Edit> it) {
		return (it.hasNext() ? it.next() : END_EDIT);
	}
}
