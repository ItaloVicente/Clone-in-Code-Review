	public static final DiffAlgorithm INSTANCE = new LowLevelDiffAlgorithm() {
		@Override
		public <S extends Sequence> void diffNonCommon(EditList edits
				HashedSequenceComparator<S> cmp
				HashedSequence<S> b
			new MyersDiff<S>(edits
