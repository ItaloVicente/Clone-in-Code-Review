		IPath newLocation = null;
		if (description.getLocationURI() != null)
			newLocation = URIUtil.toPath(description.getLocationURI());
		else
			newLocation = source.getProject().getWorkspace().getRoot().getLocation().append(description.getName());
		IPath sourceLocation = source.getLocation();
		if (sourceLocation.isPrefixOf(newLocation)) {
			tree.failed(new Status(IStatus.ERROR, Activator.getPluginId(), 0, "Cannot move project. See https://bugs.eclipse.org/bugs/show_bug.cgi?id=339814", null)); //$NON-NLS-1$
			return true;
		}
		if (!srcm.getGitDir().startsWith("../")) { //$NON-NLS-1$
			tree.failed(new Status(IStatus.ERROR, Activator.getPluginId(), 0, "Cannot move project. Project contains Git Repo", null)); //$NON-NLS-1$
			return true;		}
		File newLocationFile = newLocation.toFile();
