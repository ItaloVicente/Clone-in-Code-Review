			StatusManager
					.getManager()
					.handle(
							new Status(
									IStatus.WARNING,
									WorkbenchPlugin.PI_WORKBENCH,
									WorkbenchMessages.BundleSigningTray_Cant_Find_Service),
							StatusManager.LOG);
