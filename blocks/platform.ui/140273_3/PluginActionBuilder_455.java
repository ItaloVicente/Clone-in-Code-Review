		}

		protected void contributeMenu(IConfigurationElement menuElement, IMenuManager mng, boolean appendIfMissing) {
			String id = menuElement.getAttribute(IWorkbenchRegistryConstants.ATT_ID);
			String label = menuElement.getAttribute(IWorkbenchRegistryConstants.ATT_LABEL);
			String path = menuElement.getAttribute(IWorkbenchRegistryConstants.ATT_PATH);
			String icon = menuElement.getAttribute(IWorkbenchRegistryConstants.ATT_ICON);
			ImageDescriptor image = null;
			if (icon != null) {
				String extendingPluginId = menuElement.getDeclaringExtension().getContributor().getName();
				image = AbstractUIPlugin.imageDescriptorFromPlugin(extendingPluginId, icon);
			}
			if (label == null) {
