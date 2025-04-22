	public static List<IProject> selectionToProjects(IStructuredSelection selection) {
		if (selection == null) {
			return Collections.emptyList();
		}
		List<IProject> resources = new ArrayList<>();
		Iterator<?> iter = selection.iterator();
		while (iter.hasNext()) {
			Object curr = iter.next();
			if (curr instanceof IWorkingSet) {
				IWorkingSet workingSet = (IWorkingSet) curr;
				if (workingSet.isAggregateWorkingSet() && workingSet.isEmpty()) {
					continue;
				}
				IAdaptable[] elements = workingSet.getElements();
				for (IAdaptable element : elements) {
					IProject project = element.getAdapter(IProject.class);
					if (project != null) {
						resources.add(project);
					}
				}
			} else if (curr instanceof IAdaptable) {
				IProject resource = ((IAdaptable) curr).getAdapter(IProject.class);
				if (resource != null) {
					resources.add(resource);
				}
			}
		}
		return resources;
	}

