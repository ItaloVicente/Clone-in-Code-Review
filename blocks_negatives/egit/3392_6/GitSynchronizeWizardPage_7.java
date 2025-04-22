		final Object[] array = repositories.keySet().toArray();
		treeViewer.setInput(array);
		if (selectProjects == null)
			treeViewer.setCheckedElements(array);
		else
			treeViewer.setCheckedElements(selectProjects);
		repositoriesColumn.getColumn().pack();

		save();

		treeViewer.addCheckStateListener(new ICheckStateListener() {
			public void checkStateChanged(CheckStateChangedEvent event) {
				selectedRepositories.clear();
				selectedProjects.clear();

				save();
				validatePage();
