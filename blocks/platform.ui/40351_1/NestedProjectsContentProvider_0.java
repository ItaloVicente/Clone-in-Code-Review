	@Override
	public void resourceChanged(IResourceChangeEvent event) {
		if (event.getDelta().getKind() == IResourceDelta.CHANGED && event.getDelta().getResource().getType() == IResource.ROOT) {
			final Set<IContainer> parentsToRefresh = new HashSet<IContainer>();
			for (IResourceDelta delta : event.getDelta().getAffectedChildren()) {
				if (delta.getResource().getType() == IResource.PROJECT && delta.getKind() == IResourceDelta.ADDED) {
					IProject newProject = (IProject)delta.getResource();
					if (NestedProjectManager.isShownAsNested(newProject)) {
						parentsToRefresh.add(getParent(newProject));
					}
				}
			}
			if (!parentsToRefresh.isEmpty()) {
				this.viewer.getTree().getDisplay().asyncExec(new Runnable() {
					@Override
					public void run() {
						for (IContainer parent : parentsToRefresh) {
							NestedProjectsContentProvider.this.viewer.refresh(parent);
						}
					}
				});
			}
		}
	}

