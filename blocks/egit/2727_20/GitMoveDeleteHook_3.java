		IPath newLocation = null;
		if (description.getLocationURI() != null)
			newLocation = URIUtil.toPath(description.getLocationURI());
		else
			newLocation = source.getWorkspace().getRoot().getLocation()
					.append(description.getName());
		IPath sourceLocation = source.getLocation();
		if (sourceLocation.isPrefixOf(newLocation)
				&& sourceLocation.segmentCount() != newLocation.segmentCount()
				&& !"true".equals(System.getProperty("egit.assume_307140_fixed"))) { //$NON-NLS-1$//$NON-NLS-2$
			tree.failed(new Status(
					IStatus.ERROR,
					Activator.getPluginId(),
					0,
					"Cannot move project. See https://bugs.eclipse.org/bugs/show_bug.cgi?id=307140 (not resolved in 3.7)", //$NON-NLS-1$
					null));
			return true;
		}
		File newLocationFile = newLocation.toFile();
