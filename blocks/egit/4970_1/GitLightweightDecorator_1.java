		final IResource resource = getResource(element);
		if (resource == null) {
			decorateResourceMapping(element, decoration);
		} else {
			try {
				decorateResource(resource, decoration);
			} catch(CoreException e) {
				handleException(resource, e);
			}
		}
	}

	private void decorateResource(IResource resource, IDecoration decoration) throws CoreException {
		IndexDiffData indexDiffData = getIndexDiffDataOrNull(resource);

		if(indexDiffData == null)
