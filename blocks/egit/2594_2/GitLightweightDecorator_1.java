			final Boolean notDecoratable = (Boolean) resource
					.getSessionProperty(NOT_DECORATABLE_KEY);
			if (notDecoratable != null && notDecoratable.equals(Boolean.TRUE)) {
				return;
			} else {
				final Long refreshed = (Long) resource
						.getSessionProperty(REFRESHED_KEY);
				if (refreshed != null) {
					final Long refresh = (Long) resource.getWorkspace()
							.getRoot().getSessionProperty(REFRESH_KEY);
					if (refresh == null
							|| refresh.longValue() <= refreshed.longValue()) {
						final IDecoratableResource decoratableResource = (IDecoratableResource) resource
								.getSessionProperty(DECORATABLE_RESOURCE_KEY);
						if (decoratableResource != null) {
							final DecorationHelper helper = new DecorationHelper(
									activator.getPreferenceStore());
							helper.decorate(decoration, decoratableResource);
							return;
						}
