	    for (TableItem item : children) {
		IProject data = (IProject) item.getData();
		RepositoryFinder repositoryFinder = new RepositoryFinder(data);
		repositoryFinder.setFindInChildren(false);
		try {
		    Collection<RepositoryMapping> find = repositoryFinder
			    .find(new NullProgressMonitor());
		    if (find.size() != 1)
			item.setChecked(true);
		} catch (CoreException e1) {
		    item.setText(2, e1.getMessage());
