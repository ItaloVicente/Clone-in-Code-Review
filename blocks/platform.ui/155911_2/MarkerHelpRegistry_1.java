	private static boolean canLoadExtensionWithoutActivation(Bundle bundle) {
		int state = bundle.getState();
		if (state == Bundle.ACTIVE) {
			return true;
		}
		if (state == Bundle.RESOLVED) {
			Dictionary<String, String> manifest = bundle.getHeaders();
			if (manifest.get(Constants.BUNDLE_ACTIVATOR) == null
					&& manifest.get(Constants.BUNDLE_ACTIVATIONPOLICY) == null) {
				return true;
			}
		}
		return false;
	}

	private static IMarkerResolutionGenerator createGeneratorFromActiveBundle(IConfigurationElement element) {
		IMarkerResolutionGenerator generator;
		try {
			generator = (IMarkerResolutionGenerator) element.createExecutableExtension(ATT_CLASS);
		} catch (CoreException e) {
			Policy.handle(e);
			generator = GENERATOR_ERROR;
		}
		return generator;
	}

