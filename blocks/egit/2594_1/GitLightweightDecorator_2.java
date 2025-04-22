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
											"Could not decorate resource: " + resources[i].getFullPath()); //$NON-NLS-1$
