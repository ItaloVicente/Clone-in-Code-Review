		if (resourceName != null && !resourceName.isEmpty()) {
			if (resourceName.endsWith("/")) { //$NON-NLS-1$
				validateName(""); //$NON-NLS-1$
				return false;
			}
			IPath resourcePath = new Path(resourceName);
			if (resourcePath.segmentCount() > 1) {
				for (String segment : resourcePath.segments()) {
					if (!validateName(segment)) {
						return false;
					}
				}
			} else {
				if (!validateName(resourceName)) {
					return false;
				}
			}
		} else {
			if (!validateName(resourceName)) {
				return false;
			}
