		IExtension[] extensions = point.getExtensions();
		if (extensions == null) {
			return null;
		}
		for (IExtension extension : extensions) {
			IConfigurationElement[] configElements = extension.getConfigurationElements();
			for (IConfigurationElement configElement : configElements) {
				if (Objects.equals(perspectiveId, id)) {
					return showInId;
				}
			}
		}
		return null;
