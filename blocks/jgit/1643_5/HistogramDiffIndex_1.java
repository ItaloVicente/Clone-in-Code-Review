
package org.eclipse.jgit.diff;

public class HistogramDiff extends DiffAlgorithm {
	private DiffAlgorithm fallback = MyersDiff.INSTANCE;

	private int maxChainLength = 64;

	public void setFallbackAlgorithm(DiffAlgorithm alg) {
		fallback = alg;
	}

	public void setMaxChainLength(int maxLen) {
		maxChainLength = maxLen;
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
			Edit lcs = new HistogramDiffIndex<S>(maxChainLength
					.findLongestCommonSequence();
			if (lcs != null) {
				if (lcs.isEmpty()) {
					edits.add(r);
				} else {
					diff(r.before(lcs));
					diff(r.after(lcs));
				}

			} else if (fallback != null) {
				SubsequenceComparator<HashedSequence<S>> cs = subcmp();
				Subsequence<HashedSequence<S>> as = Subsequence.a(a
				Subsequence<HashedSequence<S>> bs = Subsequence.b(b

				EditList res = fallback.diffNonCommon(cs
				edits.addAll(Subsequence.toBase(res

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

		private SubsequenceComparator<HashedSequence<S>> subcmp() {
			return new SubsequenceComparator<HashedSequence<S>>(cmp);
		}
	}
}
