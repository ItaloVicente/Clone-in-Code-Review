		for (Object location : elements) {
			Repository repo = null;
			if (location instanceof IResource) {
				repo = getRepository((IResource) location);
			} else if (location instanceof IPath) {
				repo = getRepository((IPath) location);
