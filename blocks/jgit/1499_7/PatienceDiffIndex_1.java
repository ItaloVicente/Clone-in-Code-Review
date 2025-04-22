
package org.eclipse.jgit.diff;

public class PatienceDiff implements DiffAlgorithm {
	private DiffAlgorithm fallback;

	public void setFallbackAlgorithm(DiffAlgorithm alg) {
		fallback = alg;
	}

	public <S extends Sequence
			C cmp
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
			State<S
			d.diff(e
			return d.edits;
		}

		case EMPTY:
			return new EditList();

		default:
			throw new IllegalStateException();
		}
	}

	private class State<S extends Sequence
		private final C cmp;

		private final S a;

		private final S b;

		final EditList edits;

		State(C cmp
			this.cmp = cmp;
			this.a = a;
			this.b = b;
			this.edits = new EditList();
		}

		private void diff(Edit e
			switch (e.getType()) {
			case INSERT:
			case DELETE:
				edits.add(e);
				return;

			case REPLACE:
				break;

			case EMPTY:
			default:
				throw new IllegalStateException();
			}

			PatienceDiffIndex<S

			p = new PatienceDiffIndex<S
			Edit lcs = p.findLongestCommonSequence();

			if (lcs != null) {
				pCommon = p.nCommon;
				pIdx = p.cIdx;
				pEnd = p.nCnt;
				p = null;

				diff(e.before(lcs)
				diff(e.after(lcs)

			} else if (fallback != null) {
				p = null;

				SubsequenceComparator<S> c = new SubsequenceComparator<S>(cmp);
				Subsequence<S> x = new Subsequence<S>(a
				Subsequence<S> y = new Subsequence<S>(b

				for (Edit r : fallback.diff(c
					r.beginA += e.beginA;
					r.beginB += e.beginB;
					r.endA += e.beginA;
					r.endB += e.beginB;
					edits.add(r);
				}

			} else {
				edits.add(e);
			}
		}
	}
}
