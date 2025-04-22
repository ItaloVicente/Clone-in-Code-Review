		final Object[] array = repositories.keySet().toArray();
		treeViewer.setInput(array);
		if (selectProjects == null)
			treeViewer.setCheckedElements(array);
		else
			treeViewer.setCheckedElements(selectProjects);
		repositoriesColumn.getColumn().pack();
