		try {
			DecorationHelper helper = new DecorationHelper(activator
					.getPreferenceStore());
			helper.decorate(decoration,
					new DecoratableResourceAdapter(resource));
		} catch (IOException e) {
			handleException(resource, new CoreException(new Status(
					IStatus.ERROR, Activator.getPluginId(), e.getMessage(), e)));
