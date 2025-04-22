		} else if (event.getType() == IResourceChangeEvent.POST_CHANGE) {
			IResourceDelta delta = event.getDelta();
			if (delta != null) {
				resource = delta.getResource();
				if (delta.getKind() == IResourceDelta.CHANGED && resource != null
						&& resource.getType() == IResource.ROOT) {
					for (IResourceDelta childDelta : event.getDelta().getAffectedChildren()) {
						IResource childResource = childDelta.getResource();
						if (childResource != null && childResource.getType() == IResource.PROJECT
								&& childDelta.getKind() == IResourceDelta.ADDED) {
							IProject newProject = (IProject) childResource;
							IContainer parent = getParent(newProject);
							if (parent != null) {
								parentsToRefresh.add(parent);
							}
