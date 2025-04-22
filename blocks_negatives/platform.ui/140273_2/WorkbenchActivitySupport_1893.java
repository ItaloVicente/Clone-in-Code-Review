			activityImageBindingRegistry = new ImageBindingRegistry(IWorkbenchRegistryConstants.TAG_ACTIVITY_IMAGE_BINDING);
			PlatformUI
					.getWorkbench()
					.getExtensionTracker()
					.registerHandler(
							activityImageBindingRegistry,
							ExtensionTracker
									.createExtensionPointFilter(getActivitySupportExtensionPoint()));
