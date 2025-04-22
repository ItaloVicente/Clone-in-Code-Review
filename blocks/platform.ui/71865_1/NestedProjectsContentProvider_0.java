		if (event.getType() == IResourceChangeEvent.PRE_DELETE && event.getResource().getType() == IResource.PROJECT) {
			IProject aboutToBeDeleted = (IProject) event.getResource();
			IContainer parent = getParent(aboutToBeDeleted);
			if (parent != null) {
				parentsToRefresh.add(parent);
			}
		} else if (event.getType() == IResourceChangeEvent.POST_CHANGE) {
			if (event.getDelta().getKind() == IResourceDelta.CHANGED
					&& event.getDelta().getResource().getType() == IResource.ROOT) {
				for (IResourceDelta delta : event.getDelta().getAffectedChildren()) {
					if (delta.getResource().getType() == IResource.PROJECT && delta.getKind() == IResourceDelta.ADDED) {
						IProject newProject = (IProject) delta.getResource();
						IContainer parent = getParent(newProject);
						if (parent != null) {
							parentsToRefresh.add(parent);
						}
