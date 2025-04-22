	<S extends Sequence> void diffNonCommon(EditList edits
			HashedSequenceComparator<S> cmp
			HashedSequence<S> b
		State<S> s = new State<S>(edits
		s.diffReplace(region);
	}

