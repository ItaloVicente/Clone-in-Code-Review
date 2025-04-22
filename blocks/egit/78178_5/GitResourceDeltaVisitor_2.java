			if (delta.getKind() == IResourceDelta.REMOVED) {
				IPath loc = deletedProjects.remove(resource);
				if (loc != null) {
					projectDeleted |= !loc.toFile().isDirectory();
				}
				return false;
			}
