		String iconPath = MENU_ICON;
		URL url = BundleUtility.find(PlatformUI.PLUGIN_ID, iconPath);
		menuImageDescriptor = ImageDescriptor.createFromURL(url);

		iconPath = SUBMENU_ICON;
		url = BundleUtility.find(PlatformUI.PLUGIN_ID, iconPath);
		submenuImageDescriptor = ImageDescriptor.createFromURL(url);

		iconPath = TOOLBAR_ICON;
		url = BundleUtility.find(PlatformUI.PLUGIN_ID, iconPath);
		toolbarImageDescriptor = ImageDescriptor.createFromURL(url);

		iconPath = WARNING_ICON;
		url = BundleUtility.find(PlatformUI.PLUGIN_ID, iconPath);
		warningImageDescriptor = ImageDescriptor.createFromURL(url);
