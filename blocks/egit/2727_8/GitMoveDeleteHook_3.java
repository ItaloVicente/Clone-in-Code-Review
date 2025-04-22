		IPath newLocation = null;
		if (description.getLocationURI() != null)
			newLocation = URIUtil.toPath(description.getLocationURI());
		else
			newLocation = source.getWorkspace().getRoot().getLocation()
					.append(description.getName());
		IPath sourceLocation = source.getLocation();
		Version version = Platform.getBundle("org.eclipse.platform").getVersion(); //$NON-NLS-1$
		if (version.compareTo(Version.parseVersion("3.7.0")) < 0) { //$NON-NLS-1$
			if (sourceLocation.isPrefixOf(newLocation)) {
				tree.failed(new Status(
						IStatus.ERROR,
						Activator.getPluginId(),
						0,
						"Cannot move project. " + //$NON-NLS-1$
						"See https://bugs.eclipse.org/bugs/show_bug.cgi?id=307140 (resolved in 3.7)", null)); //$NON-NLS-1$
				return true;
			}
		}
		if (!srcm.getGitDir().startsWith("../")) { //$NON-NLS-1$
			tree.failed(new Status(IStatus.ERROR, Activator.getPluginId(), 0,
					"Cannot move project. Project contains Git Repo", null)); //$NON-NLS-1$
			return true;
		}
		File newLocationFile = newLocation.toFile();
