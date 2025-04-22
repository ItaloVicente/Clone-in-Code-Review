		IDecoratableResource decoratableResource = null;
		final DecorationHelper helper = new DecorationHelper(
				activator.getPreferenceStore());


		try {
			final Boolean notDecoratable = (Boolean) resource
					.getSessionProperty(NOT_DECORATABLE_KEY);
			if (notDecoratable != null && notDecoratable.equals(Boolean.TRUE))
				return;

			decoratableResource = (IDecoratableResource) resource
					.getSessionProperty(DECORATABLE_RESOURCE_KEY);
			if (decoratableResource != null) {
				final Long refreshed = (Long) resource
						.getSessionProperty(REFRESHED_KEY);
				if (refreshed != null) {
					final Long refresh = (Long) resource.getWorkspace()
							.getRoot().getSessionProperty(REFRESH_KEY);
					if (refresh == null
							|| refresh.longValue() <= refreshed.longValue()) {
						helper.decorate(decoration, decoratableResource);
						return;
					}
				}
			}
		} catch (CoreException e) {
			handleException(resource, e);
			return;
		}


