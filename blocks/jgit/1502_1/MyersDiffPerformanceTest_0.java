	}

	private static class CharCmp extends DiffComparator<CharArray> {
		@Override
		public int size(CharArray seq) {
			return seq.array.length;
		}
