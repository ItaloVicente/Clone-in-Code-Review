		String pathKey;
		int firstSlash = path.indexOf("/"); //$NON-NLS-1$
		if (firstSlash > -1)
			pathKey = path.substring(0, firstSlash);
		else
			pathKey = path;

		IPath fullPath = getLocation().append(pathKey);
		if (path.contains("/")) { //$NON-NLS-1$
