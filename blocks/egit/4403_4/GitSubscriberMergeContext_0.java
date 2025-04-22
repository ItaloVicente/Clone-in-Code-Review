				IResourceDelta[] affectedChildren = event.getDelta()
						.getAffectedChildren();
				for (IResourceDelta delta : affectedChildren) {
					if (delta.getFlags() == 0)
						continue;
					IResource resource = delta.getResource();
					RepositoryMapping repo = getMapping(resource);
