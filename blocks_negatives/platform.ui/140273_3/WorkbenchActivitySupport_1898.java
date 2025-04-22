			categoryImageBindingRegistry = new ImageBindingRegistry(IWorkbenchRegistryConstants.TAG_CATEGORY_IMAGE_BINDING);
			PlatformUI
			.getWorkbench()
			.getExtensionTracker()
			.registerHandler(
					categoryImageBindingRegistry,
					ExtensionTracker
							.createExtensionPointFilter(getActivitySupportExtensionPoint()));
