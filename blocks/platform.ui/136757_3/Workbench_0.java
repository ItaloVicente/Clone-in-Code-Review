				IExtensionPoint point = registry.getExtensionPoint(PlatformUI.PLUGIN_ID,
						IWorkbenchRegistryConstants.PL_STARTUP);

				IExtension[] extensions = point.getExtensions();
				if (extensions.length == 0) {
					return Status.OK_STATUS;
				}
