
package org.eclipse.jgit.diff;

public class HistogramDiff extends DiffAlgorithm {
	public static final HistogramDiff INSTANCE = new HistogramDiff();

	private HistogramDiff() {
	}

	public <S extends Sequence> EditList diffNonCommon(
			SequenceComparator<? super S> cmp
		State<S> s = new State<S>(new HashedSequencePair<S>(cmp
		s.diffReplace(new Edit(0
		return s.edits;
	}

	private class State<S extends Sequence> {
		private final HashedSequenceComparator<S> cmp;

		private final HashedSequence<S> a;

		private final HashedSequence<S> b;

		final EditList edits;

		State(HashedSequencePair<S> p) {
			this.cmp = p.getComparator();
			this.a = p.getA();
			this.b = p.getB();
			this.edits = new EditList();
		}

		void diffReplace(Edit r) {
			Edit lcs = new HistogramDiffIndex<S>(cmp
					.findLongestCommonSequence();
			if (lcs != null) {
				diff(r.before(lcs));
				diff(r.after(lcs));
			} else {
				edits.add(r);
			}
		}

		private void diff(Edit r) {
			switch (r.getType()) {
			case INSERT:
			case DELETE:
				edits.add(r);
				break;

			case REPLACE:
				diffReplace(r);
				break;

			case EMPTY:
			default:
				throw new IllegalStateException();
			}
		}
	}
}
