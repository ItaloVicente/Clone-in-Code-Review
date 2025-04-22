		if ((changeFlags & IResourceDelta.MARKERS) != 0) {
			IProject project = resource.getProject();
			if (project != null) {
				toRefresh.add(project);
				return;
			}
		}
