		try {
			IDecoratableResource[] decoratableResources = DecoratableResourceHelper
					.createDecoratableResources(resources);

			for (int i = 0; i < decoratableResources.length; i++) {
				try {
					if (decoratableResources[i] != null) {
						resources[i].setSessionProperty(
								DECORATABLE_RESOURCE_KEY,
								decoratableResources[i]);
						resources[i].setSessionProperty(REFRESHED_KEY,
								Long.valueOf(System.currentTimeMillis()));
					} else {
						if (resources[i] != null)
							handleException(
									resources[i],
									new CoreException(
											Activator
													.createErrorStatus(UIText.GitLightweightDecorator_ResourceError)));
