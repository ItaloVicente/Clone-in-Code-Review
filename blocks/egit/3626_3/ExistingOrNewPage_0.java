		TableItem[] children = projectMoveViewer.getTable().getItems();
		for (int i = 0; i < children.length; i++) {
			TableItem item = children[i];
			IProject data = (IProject) item.getData();
			RepositoryFinder repositoryFinder = new RepositoryFinder(data);
			try {
				Collection<RepositoryMapping> find = repositoryFinder
						.find(new NullProgressMonitor());
				if (find.size() != 1)
					item.setChecked(true);
			} catch (CoreException e1) {
				item.setText(2, e1.getMessage());
			}
		}
