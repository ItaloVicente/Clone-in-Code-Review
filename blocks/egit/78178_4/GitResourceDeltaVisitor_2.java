			if (delta.getKind() == IResourceDelta.REMOVED) {
				IPath loc = deletedProjects.get(resource);
				if (loc != null) {
					projectDeleted |= !loc.toFile().isDirectory();
					deletedProjects.remove(resource);
				}
				return false;
			}
