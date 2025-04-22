		if (in.getItems().length == 1) {
			IResource resource = in.getItems()[0];
			final String type;
			switch (resource.getType()) {
			case IResource.FILE:
				type = "File"; //$NON-NLS-1$ TODO
				break;
			case IResource.PROJECT:
				type = "Project"; //$NON-NLS-1$ TODO
				break;
			default:
				type = "Folder"; //$NON-NLS-1$ TODO
				break;
			}
			return NLS.bind(pattern, new Object[] { type,
					resource.getFullPath().makeRelative(), repositoryName });
		} else {
			StringBuilder b = new StringBuilder();
