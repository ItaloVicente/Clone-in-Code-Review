		IResourceDelta delta = event.getDelta();
		if (delta == null || event.getType() != IResourceChangeEvent.POST_CHANGE) {
			return;
		}
		final Set<IContainer> parentsToRefresh = new LinkedHashSet<>();
		if (delta.getKind() == IResourceDelta.CHANGED && delta.getResource() instanceof IWorkspaceRoot) {
			for (IResourceDelta childDelta : event.getDelta().getAffectedChildren()) {
				IResource childResource = childDelta.getResource();
				if (childResource instanceof IProject && (childDelta.getKind() == IResourceDelta.ADDED
						|| childDelta.getKind() == IResourceDelta.REMOVED)) {
					IProject affectedProject = (IProject) childResource;
					IContainer parent = getParent(affectedProject);
					if (parent != null) {
						parentsToRefresh.add(parent);
					} else {
						parentsToRefresh.add(affectedProject.getParent());
