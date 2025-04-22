	private IProject[] getSelectedProjects(ExecutionEvent event)
			throws ExecutionException {
		IStructuredSelection selection = getSelection(event);
		return getSelectedProjects(selection);
	}

	private IProject[] getSelectedProjects(IStructuredSelection selection) {
		IResource[] selectedResources = getSelectedResources(selection);
		if (selectedResources.length == 0)
			return new IProject[0];
		ArrayList<IProject> projects = new ArrayList<IProject>();
		for (int i = 0; i < selectedResources.length; i++) {
			IResource resource = selectedResources[i];
			if (resource.getType() == IResource.PROJECT)
				projects.add((IProject) resource);
		}
		return projects.toArray(new IProject[projects.size()]);
	}

