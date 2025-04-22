		Iterator resources = getSelectedResources().iterator();
		while (resources.hasNext()) {
			IProject project = (IProject) resources.next();
			if (!project.isOpen())
				closedInSelection++;
		}
