	private static List<IProject> selectionToProjects(IStructuredSelection selection) {
		if (selection == null) {
			return Collections.emptyList();
		}
		List<IProject> resources = new ArrayList<>();
		Iterator<?> selectionIterator = selection.iterator();
		while (selectionIterator.hasNext()) {
			Object currentObject = selectionIterator.next();
			if (currentObject instanceof IWorkingSet) {
				IWorkingSet workingSet = (IWorkingSet) currentObject;
				for (IAdaptable element : workingSet.getElements()) {
					IProject project = element.getAdapter(IProject.class);
					if (project != null) {
						resources.add(project);
					}
				}
			} else if (currentObject instanceof IAdaptable) {
				IProject resource = ((IAdaptable) currentObject).getAdapter(IProject.class);
				if (resource != null) {
					resources.add(resource);
				}
			}
		}
		return resources;
	}

