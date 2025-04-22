	private void runProcessors(IExtension[] extensions, boolean initial, boolean afterFragments) {
		for (IExtension extension : extensions) {
			IConfigurationElement[] ces = extension.getConfigurationElements();
			for (IConfigurationElement ce : ces) {
						runProcessor(ce);
					}
				}
			}
		}
	}
