				int mask = IResourceDelta.ADDED | IResourceDelta.CHANGED
						| IResourceDelta.REMOVED;
				IResourceDelta[] affectedChildren = event.getDelta()
						.getAffectedChildren(mask);
				for (IResourceDelta delta : affectedChildren) {
					IResource resource = delta.getResource();
					RepositoryMapping repo = RepositoryMapping.getMapping(resource);
