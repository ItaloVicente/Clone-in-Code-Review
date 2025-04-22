		public void inputChanged(Viewer viewer, Object oldInput,
				Object newInput) {
			folders.clear();
			parents.clear();
			if (newInput instanceof DiffDocument) {
				computeFolders(((DiffDocument) newInput).getFileRegions());
			}
		}

		@Override
		public void dispose() {
