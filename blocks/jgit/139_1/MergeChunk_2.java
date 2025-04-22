
package org.eclipse.jgit.merge;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jgit.diff.Edit;
import org.eclipse.jgit.diff.EditList;
import org.eclipse.jgit.diff.MyersDiff;
import org.eclipse.jgit.diff.Sequence;
import org.eclipse.jgit.merge.MergeChunk.ConflictState;

public class MergeAlgorithm {
	public static MergeResult merge(Sequence base
		List<Sequence> sequences=new ArrayList<Sequence>(3);
		sequences.add(base);
		sequences.add(ours);
		sequences.add(theirs);
		MergeResult result=new MergeResult(sequences);
		EditList oursEdits = new MyersDiff(base
		Iterator<Edit> baseToOurs=oursEdits.iterator();
		EditList theirsEdits = new MyersDiff(base
		Iterator<Edit> baseToTheirs=theirsEdits.iterator();
		Edit oursEdit = baseToOurs.hasNext() ? baseToOurs.next() : null;
		Edit theirsEdit = baseToTheirs.hasNext() ? baseToTheirs.next() : null;

		while(theirsEdit!=null || oursEdit!=null) {
			switch ((oursEdit==null) ? 1 : oursEdit.compareTo(theirsEdit)) {
			case -1:

				result.add(0

				result.add(1
				current=oursEdit.getEndA();
				oursEdit = baseToOurs.hasNext() ? baseToOurs.next() : null;
				break;

			case 1:

				result.add(0

				result.add(2
				current=theirsEdit.getEndA();
				theirsEdit = baseToTheirs.hasNext() ? baseToTheirs.next() : null;
				break;

			case 0:

				result.add(0

				int oursBeginB=oursEdit.getBeginB();
				int theirsBeginB=theirsEdit.getBeginB();
				int oursEndB=oursEdit.getEndB();
				int theirsEndB=theirsEdit.getEndB();

				if (oursEdit.getBeginA()<theirsEdit.getBeginA()) {
					theirsBeginB-=theirsEdit.getBeginA()-oursEdit.getBeginA();
				} else {
					oursBeginB-=oursEdit.getBeginA()-theirsEdit.getBeginA();
				}

				Edit nextOursEdit = baseToOurs.hasNext() ? baseToOurs.next() : null;
				Edit nextTheirsEdit = baseToTheirs.hasNext() ? baseToTheirs.next() : null;
				for(;;) {
					if (nextTheirsEdit!=null && oursEdit.getEndA()>nextTheirsEdit.getBeginA()) {
						theirsEndB=nextTheirsEdit.getEndB();
						theirsEdit=nextTheirsEdit;
						nextTheirsEdit = baseToTheirs.hasNext() ? baseToTheirs.next() : null;
					} else if (nextOursEdit!=null && theirsEdit.getEndA()>nextOursEdit.getBeginA()) {
						oursEndB=nextOursEdit.getEndB();
						oursEdit=nextOursEdit;
						nextOursEdit = baseToOurs.hasNext() ? baseToOurs.next() : null;
					} else {
						break;
					}
				}

				if (oursEdit.getEndA()<theirsEdit.getEndA()) {
					oursEndB+=theirsEdit.getEndA()-oursEdit.getEndA();
				} else {
					theirsEndB+=oursEdit.getEndA()-theirsEdit.getEndA();
				}

				result.add(1
				result.add(2

				current=Math.max(oursEdit.getEndA()
				oursEdit=nextOursEdit;
				theirsEdit=nextTheirsEdit;
			}
		}
		if (current < base.size()) {
			result.add(0
		}

		return result;
	}
}
