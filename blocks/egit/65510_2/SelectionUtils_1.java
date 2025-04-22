		for (Object location : elements) {
			Repository repo = null;
			if (location instanceof IProject) {
				repo = getRepository((IProject) location);
			} else if (location instanceof IPath) {
				repo = getRepository((IPath) location);
