		save();

		treeViewer.addCheckStateListener(new ICheckStateListener() {
			public void checkStateChanged(CheckStateChangedEvent event) {
				selectedRepositories.clear();
				selectedProjects.clear();

				save();
				validatePage();
