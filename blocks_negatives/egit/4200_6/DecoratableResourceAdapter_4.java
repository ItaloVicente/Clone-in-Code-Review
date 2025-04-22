		@Override
		public TreeFilter clone() {
			RecursiveStateFilter clone = new RecursiveStateFilter();
			clone.filesChecked = this.filesChecked;
			return clone;
		}
