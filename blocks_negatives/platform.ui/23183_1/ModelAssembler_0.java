		for (IExtension extension : extensions) {
			IConfigurationElement[] ces = extension.getConfigurationElements();
			for (IConfigurationElement ce : ces) {
					continue;
				}
				runProcessor(ce);
			}
		}
