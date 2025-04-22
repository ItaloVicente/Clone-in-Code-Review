			IDecoratableResource decoratableResource = null;
			final DecorationHelper helper = new DecorationHelper(
					activator.getPreferenceStore());

			final Long refreshed = (Long) resource
					.getSessionProperty(REFRESHED_KEY);
			if (refreshed != null) {
				final IWorkspaceRoot root = resource.getWorkspace().getRoot();
				final Long refresh = (Long) root
						.getSessionProperty(REFRESH_KEY);
				if (refresh == null
						|| refresh.longValue() < refreshed.longValue()) {
					decoratableResource = (IDecoratableResource) resource
							.getSessionProperty(DECORATABLE_RESOURCE_KEY);
					if (decoratableResource != null) {
						helper.decorate(decoration, decoratableResource);
						return;
					}
				}
			}

			decoratableResource = new DecoratableResourceAdapter(resource);
			helper.decorate(decoration, decoratableResource);

			resource.setSessionProperty(DECORATABLE_RESOURCE_KEY,
					decoratableResource);
			resource.setSessionProperty(REFRESHED_KEY,
					new Long(System.currentTimeMillis()));
