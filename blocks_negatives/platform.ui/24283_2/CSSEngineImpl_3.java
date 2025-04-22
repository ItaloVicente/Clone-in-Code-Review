	/** @return true if some providers were found */
	private boolean configureElementProviders(String extensionPointId) {
		IExtensionPoint extPoint = registry.getExtensionPoint(extensionPointId);
		if (extPoint == null) {
			return false;
		}
		IExtension[] extensions = extPoint.getExtensions();
		if (extensions.length == 0) {
			return false;
		}
		for (IExtension e : extensions) {
			for (IConfigurationElement ce : e.getConfigurationElements()) {
				String tmp = ce.getName();
				if (tmp.equals("provider")) {
					try {
						Object tmp2 = ce.createExecutableExtension("class");
						for (IConfigurationElement ce2 : ce.getChildren()) {
							String widget = ce2.getAttribute("class");
							widgetsMap.put(widget, tmp2);
						}
					} catch (CoreException e1) {
						e1.printStackTrace();
					}
				}
			}
		}
		return true;
	}

