		try {
			final IDecoratableResource decoratableResource = (IDecoratableResource) resource
					.getSessionProperty(DECORATABLE_RESOURCE_KEY);
			if (decoratableResource != null) {
				final DecorationHelper helper = new DecorationHelper(
						activator.getPreferenceStore());
				helper.decorate(decoration, decoratableResource);
				resource.setSessionProperty(DECORATABLE_RESOURCE_KEY, null);
				return;
			}
		} catch (CoreException e) {
			handleException(resource, new CoreException(new Status(
					IStatus.ERROR, Activator.getPluginId(), e.getMessage(), e)));
			return;
		}

		if (!PlatformUI.isWorkbenchRunning())
			return;

