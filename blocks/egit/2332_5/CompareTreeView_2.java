	private void setResourceInput(final IResource[] input) {
		if (input.length > 0) {
			List<IResource> resources = new ArrayList<IResource>(input.length);
			List<IPath> allPaths = new ArrayList<IPath>(input.length);
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
					resources.add(originalInput);
			}
			this.input = resources.toArray(new IResource[resources.size()]);
		} else
			this.input = input;
	}

