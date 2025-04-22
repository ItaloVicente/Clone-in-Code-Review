	}

	private static class CharCmp extends DiffComparator<CharArray> {
		@Override
		public int size(CharArray seq) {
			return seq.array.length;
		}

		@Override
		public boolean equals(CharArray a
			return a.array[ai] == b.array[bi];
		}

		@Override
		public int hash(CharArray seq
			return seq.array[ptr];
