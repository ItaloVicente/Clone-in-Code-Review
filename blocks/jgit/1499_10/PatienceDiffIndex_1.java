
package org.eclipse.jgit.diff;

public class PatienceDiff implements DiffAlgorithm {
	private DiffAlgorithm fallback;

	public void setFallbackAlgorithm(DiffAlgorithm alg) {
		fallback = alg;
	}

	public <S extends Sequence
			C cmp
		Edit region = new Edit(0
		region = cmp.reduceCommonStartEnd(a

		switch (region.getType()) {
		case INSERT:
		case DELETE: {
			EditList r = new EditList();
			r.add(region);
			return r;
		}

		case REPLACE: {
			SubsequenceComparator<S> cs = new SubsequenceComparator<S>(cmp);
			Subsequence<S> as = Subsequence.a(a
			Subsequence<S> bs = Subsequence.b(b
			return Subsequence.toBase(diffImpl(cs
		}

		case EMPTY:
			return new EditList();

		default:
			throw new IllegalStateException();
		}
	}

	private <S extends Sequence
			C cmp
		State<S> s = new State<S>(new HashedSequencePair<S>(cmp
		s.diff(new Edit(0
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

		private void diff(Edit r
			switch (r.getType()) {
			case INSERT:
			case DELETE:
				edits.add(r);
				return;

			case REPLACE:
				break;

			case EMPTY:
			default:
				throw new IllegalStateException();
			}

			PatienceDiffIndex<S> p;

			p = new PatienceDiffIndex<S>(cmp
			Edit lcs = p.findLongestCommonSequence();

			if (lcs != null) {
				pCommon = p.nCommon;
				pIdx = p.cIdx;
				pEnd = p.nCnt;
				p = null;

				diff(r.before(lcs)
				diff(r.after(lcs)

			} else if (fallback != null) {
				p = null;
				pCommon = null;

				SubsequenceComparator<HashedSequence<S>> cs;
				cs = new SubsequenceComparator<HashedSequence<S>>(cmp);

				Subsequence<HashedSequence<S>> as = Subsequence.a(a
				Subsequence<HashedSequence<S>> bs = Subsequence.b(b
				EditList res = fallback.diff(cs
				edits.addAll(Subsequence.toBase(res

			} else {
				edits.add(r);
			}
		}
	}
}
