	private IResource[] convertResourceInput(final IResource[] input) {
		if (input.length > 0) {
			List<IResource> resourceList = new ArrayList<>(
					input.length);
			List<IPath> allPaths = new ArrayList<>(input.length);
			for (IResource originalInput : input) {
				allPaths.add(originalInput.getFullPath());
			}
			for (IResource originalInput : input) {
				boolean skip = false;
				for (IPath path : allPaths) {
					if (path.isPrefixOf(originalInput.getFullPath())
							&& path.segmentCount() < originalInput
									.getFullPath().segmentCount()) {
						skip = true;
						break;
					}
				}
				if (!skip)
					resourceList.add(originalInput);
			}
			return resourceList.toArray(new IResource[0]);
		} else
			return input;
