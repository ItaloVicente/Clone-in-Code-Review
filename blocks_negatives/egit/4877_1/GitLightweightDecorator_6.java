		final IResource[] resources = new IResource[elements.length];
		for (int i = 0; i < elements.length; i++) {
			if (elements[i] != null)
				resources[i] = getResource(elements[i]);
		}

		IDecoratableResource[] decoratableResources = DecoratableResourceHelper
				.createDecoratableResources(resources);

		for (int i = 0; i < decoratableResources.length; i++) {
			try {
				if (decoratableResources[i] != null) {
					resources[i].setSessionProperty(DECORATABLE_RESOURCE_KEY,
							decoratableResources[i]);
					resources[i].setSessionProperty(REFRESHED_KEY,
							Long.valueOf(System.currentTimeMillis()));
				} else {
					if (resources[i] != null) {
						resources[i].setSessionProperty(NOT_DECORATABLE_KEY,
								Boolean.TRUE);
						if (GitTraceLocation.DECORATION.isActive())
							GitTraceLocation
									.getTrace()
									.trace(GitTraceLocation.DECORATION
											.getLocation(),
					}
				}
			} catch (CoreException e) {
				handleException(resources[i], e);
			}
