			insertMenuGroup(menu, new Separator(id));
		}

		protected void contributeGroupMarker(IMenuManager menu, IConfigurationElement element) {
			String id = element.getAttribute(IWorkbenchRegistryConstants.ATT_NAME);
			if (id == null || id.length() <= 0) {
