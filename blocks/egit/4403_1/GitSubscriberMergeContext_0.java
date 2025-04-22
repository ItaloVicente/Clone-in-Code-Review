				int mask = IResourceDelta.ADDED | IResourceDelta.CHANGED
						| IResourceDelta.REMOVED | IResourceDelta.CONTENT;
				IResourceDelta[] affectedChildren = event.getDelta()
						.getAffectedChildren(mask);
				RepositoryMapping repo = null;
				for (IResourceDelta delta : affectedChildren) {
					if (repo == null) // assume that all events occur inside one repository
						repo = RepositoryMapping.getMapping(delta.getResource());
