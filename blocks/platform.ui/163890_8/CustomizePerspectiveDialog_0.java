		menuImageDescriptor = ImageDescriptor
				.createFromURLSupplier(true, () -> BundleUtility.find(PlatformUI.PLUGIN_ID, MENU_ICON));
		submenuImageDescriptor = ImageDescriptor
				.createFromURLSupplier(true, () -> BundleUtility.find(PlatformUI.PLUGIN_ID, SUBMENU_ICON));
		toolbarImageDescriptor = ImageDescriptor
				.createFromURLSupplier(true, () -> BundleUtility.find(PlatformUI.PLUGIN_ID, TOOLBAR_ICON));
		warningImageDescriptor = ImageDescriptor
				.createFromURLSupplier(true, () -> BundleUtility.find(PlatformUI.PLUGIN_ID, WARNING_ICON));
