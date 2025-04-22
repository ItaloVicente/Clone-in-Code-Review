		for (Object checkedElement : treeViewer.getCheckedElements()) {
			if (checkedElement instanceof Repository) {
				Repository repo = (Repository) checkedElement;
				if (selectedRepositories.add(repo)) {
					selectedProjects.addAll(repositories.get(repo));
				}
			} else {
				selectedProjects.add((IProject) checkedElement);
			}
		}
