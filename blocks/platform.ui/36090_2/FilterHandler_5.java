		@Override
		public int length() {
			return first.length() + 1 + last.length();
		}

		@Override
		public char charAt(int index) {
			int firstLen = first.length();
			if (index < firstLen) {
				return first.charAt(index);
			} else if (index == firstLen) {
				return '.';
			} else {
				return last.charAt(index - firstLen - 1);
