
package org.eclipse.jgit.diff;

public class PatienceDiff<S extends Sequence> {
	public static <S extends Sequence> EditList diff(SequenceComparator<S> cmp
			S a
		Edit e = new Edit(0
		e = cmp.reduceCommonStartEnd(a

		switch (e.getType()) {
		case INSERT:
		case DELETE: {
			EditList r = new EditList();
			r.add(e);
			return r;
		}

		case REPLACE: {
			PatienceDiff<S> d = new PatienceDiff<S>(cmp
			d.diff(e
			return d.edits;
		}

		case EMPTY:
			return new EditList();

		default:
			throw new IllegalStateException();
		}
	}

	private final SequenceComparator<S> cmp;

	private final S a;

	private final S b;

	private final EditList edits;

	private PatienceDiff(SequenceComparator<S> cmp
		this.cmp = cmp;
		this.a = a;
		this.b = b;
		this.edits = new EditList();
	}

	private void diff(Edit region
		switch (region.getType()) {
		case INSERT:
		case DELETE:
			edits.add(region);
			return;

		case REPLACE:
			break;

		case EMPTY:
			return;
		}

		PatienceDiffIndex<S> idx = new PatienceDiffIndex<S>(cmp
		idx.scanB(b
		idx.scanA(a
		Edit lcs = idx.match(region

		if (lcs != null) {
			pCommon = idx.nCommon;
			pIdx = idx.cIdx;
			pCnt = idx.nCnt;
			idx = null;

			diff(region.before(lcs)
			diff(region.after(lcs)

		} else {
			edits.add(region);
		}
	}
}
