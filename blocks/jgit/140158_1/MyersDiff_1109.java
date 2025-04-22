
package org.eclipse.jgit.diff;

public abstract class LowLevelDiffAlgorithm extends DiffAlgorithm {
	@Override
	public <S extends Sequence> EditList diffNonCommon(
			SequenceComparator<? super S> cmp
		HashedSequencePair<S> p = new HashedSequencePair<>(cmp
		HashedSequenceComparator<S> hc = p.getComparator();
		HashedSequence<S> ha = p.getA();
		HashedSequence<S> hb = p.getB();
		p = null;

		EditList res = new EditList();
		Edit region = new Edit(0
		diffNonCommon(res
		return res;
	}

	public abstract <S extends Sequence> void diffNonCommon(EditList edits
			HashedSequenceComparator<S> cmp
			HashedSequence<S> b
}
