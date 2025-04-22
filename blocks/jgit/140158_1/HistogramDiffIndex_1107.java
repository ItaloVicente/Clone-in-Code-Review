
package org.eclipse.jgit.diff;

import java.util.ArrayList;
import java.util.List;

public class HistogramDiff extends LowLevelDiffAlgorithm {
	DiffAlgorithm fallback = MyersDiff.INSTANCE;

	int maxChainLength = 64;

	public void setFallbackAlgorithm(DiffAlgorithm alg) {
		fallback = alg;
	}

	public void setMaxChainLength(int maxLen) {
		maxChainLength = maxLen;
	}

	@Override
	public <S extends Sequence> void diffNonCommon(EditList edits
			HashedSequenceComparator<S> cmp
			HashedSequence<S> b
		new State<>(edits
	}

	private class State<S extends Sequence> {
		private final HashedSequenceComparator<S> cmp;
		private final HashedSequence<S> a;
		private final HashedSequence<S> b;
		private final List<Edit> queue = new ArrayList<>();

		final EditList edits;

		State(EditList edits
				HashedSequence<S> a
			this.cmp = cmp;
			this.a = a;
			this.b = b;
			this.edits = edits;
		}

		void diffRegion(Edit r) {
			diffReplace(r);
			while (!queue.isEmpty())
				diff(queue.remove(queue.size() - 1));
		}

		private void diffReplace(Edit r) {
			Edit lcs = new HistogramDiffIndex<>(maxChainLength
					.findLongestCommonSequence();
			if (lcs != null) {
				if (lcs.isEmpty()) {
					edits.add(r);
				} else {
					queue.add(r.after(lcs));
					queue.add(r.before(lcs));
				}

			} else if (fallback instanceof LowLevelDiffAlgorithm) {
				LowLevelDiffAlgorithm fb = (LowLevelDiffAlgorithm) fallback;
				fb.diffNonCommon(edits

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
				if (r.getLengthA() == 1 && r.getLengthB() == 1)
					edits.add(r);
				else
					diffReplace(r);
				break;

			case EMPTY:
			default:
				throw new IllegalStateException();
			}
		}

		private SubsequenceComparator<HashedSequence<S>> subcmp() {
			return new SubsequenceComparator<>(cmp);
		}
	}
}
