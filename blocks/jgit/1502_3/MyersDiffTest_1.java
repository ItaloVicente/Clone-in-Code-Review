	}

	private static class CharCmp extends SequenceComparator<CharArray> {
		@Override
		public boolean equals(CharArray a
			return a.array[ai] == b.array[bi];
		}

		@Override
		public int hash(CharArray seq
			return seq.array[ptr];
