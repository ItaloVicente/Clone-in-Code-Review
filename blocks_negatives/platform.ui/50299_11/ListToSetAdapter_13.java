		public void handleListChange(ListChangeEvent event) {
			Set added = new HashSet();
			Set removed = new HashSet();
			ListDiffEntry[] differences = event.diff.getDifferences();
			for (int i = 0; i < differences.length; i++) {
				ListDiffEntry entry = differences[i];
				Object element = entry.getElement();
