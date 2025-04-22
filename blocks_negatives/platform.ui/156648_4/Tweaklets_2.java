	/**
	 * @param definition
	 * @return
	 */
	private static Object createTweaklet(TweakKey definition) {
		IConfigurationElement[] elements = Platform.getExtensionRegistry()
		for (IConfigurationElement element : elements) {
				try {
					tweaklets.put(definition, tweaklet);
					return tweaklet;
				} catch (CoreException e) {
					StatusManager.getManager().handle(
							StatusUtil.newStatus(IStatus.ERROR, "Error with extension " + element, e), //$NON-NLS-1$
							StatusManager.LOG);
				}
			}
		}
		return null;
	}

