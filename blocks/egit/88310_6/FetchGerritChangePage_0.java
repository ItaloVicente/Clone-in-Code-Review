
		@Override
		public int compareTo(Change o) {
			int changeDiff = this.changeNumber.compareTo(o.changeNumber);
			if (changeDiff == 0) {
				changeDiff = this.getPatchSetNumber()
						.compareTo(o.getPatchSetNumber());
			}
			return changeDiff;
		}
