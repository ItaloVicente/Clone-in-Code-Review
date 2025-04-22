		for (IExtension extension : extensions) {
			IConfigurationElement[] ces = extension.getConfigurationElements();
			for (IConfigurationElement ce : ces) {
					continue;
				}
				IContributor contributor = ce.getContributor();
				if (attrURI == null) {
					logger.warn("Unable to find location for the model extension \"{0}\"", //$NON-NLS-1$
							contributor.getName());
					continue;
				}
