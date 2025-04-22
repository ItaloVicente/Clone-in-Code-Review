		if (shouldBeAnEclipseProject(project, monitor)) {
			return true;
		}
		try {
			for (IResource child : project.members()) {
				boolean ignore = false;
				for (IPath ignoredPath : ignoredPaths) {
					ignore |= ignoredPath.isPrefixOf(child.getLocation());
					if (ignore) {
						continue;
					}
				}
				if (!ignore && child.getType() == IResource.FOLDER && ((IFolder) child).findMember(IMPORTME_FILENAME) != null) {
					return true;
				}
			}
		} catch (CoreException e) {
		}
		return false;
