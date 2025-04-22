				final DecorationHelper helper = new DecorationHelper(
						activator.getPreferenceStore());
				helper.decorate(decoration, decoratableResource);
				decorated = true;
			}

			final Long refreshed = (Long) resource
					.getSessionProperty(REFRESHED_KEY);
			if (refreshed != null) {
				final Long refresh = (Long) resource.getWorkspace().getRoot()
						.getSessionProperty(REFRESH_KEY);
				if (refresh == null
						|| refresh.longValue() <= refreshed.longValue()) {
					if (decorated)
