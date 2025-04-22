
package org.eclipse.jgit.merge;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jgit.diff.Edit;
import org.eclipse.jgit.diff.EditList;
import org.eclipse.jgit.diff.MyersDiff;
import org.eclipse.jgit.diff.Sequence;
import org.eclipse.jgit.merge.MergeChunk.ConflictState;

public final class MergeAlgorithm {

	private MergeAlgorithm() {
	}

	private final static Edit END_EDIT = new Edit(Integer.MAX_VALUE
			Integer.MAX_VALUE);

	public static MergeResult merge(Sequence base
			Sequence theirs) {
		List<Sequence> sequences = new ArrayList<Sequence>(3);
		sequences.add(base);
		sequences.add(ours);
		sequences.add(theirs);
		MergeResult result = new MergeResult(sequences);
		EditList oursEdits = new MyersDiff(base
		Iterator<Edit> baseToOurs = oursEdits.iterator();
		EditList theirsEdits = new MyersDiff(base
		Iterator<Edit> baseToTheirs = theirsEdits.iterator();
		Edit oursEdit = nextEdit(baseToOurs);
		Edit theirsEdit = nextEdit(baseToTheirs);

		while (theirsEdit != END_EDIT || oursEdit != END_EDIT) {
			if (oursEdit.getEndA() <= theirsEdit.getBeginA()) {
				if (current != oursEdit.getBeginA()) {
					result.add(0
							ConflictState.NO_CONFLICT);
				}
				result.add(1
						ConflictState.NO_CONFLICT);
				current = oursEdit.getEndA();
				oursEdit = nextEdit(baseToOurs);
			} else if (theirsEdit.getEndA() <= oursEdit.getBeginA()) {
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
					if (oursEdit.getEndA() > nextTheirsEdit.getBeginA()) {
						theirsEdit = nextTheirsEdit;
						nextTheirsEdit = nextEdit(baseToTheirs);
					} else if (theirsEdit.getEndA() > nextOursEdit.getBeginA()) {
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

				result.add(1
						ConflictState.FIRST_CONFLICTING_RANGE);
				result.add(2
						ConflictState.NEXT_CONFLICTING_RANGE);

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
