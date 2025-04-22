				if (!result) {
					WorkbenchPlugin.log("Unable to process element: " + //$NON-NLS-1$
							type + " in perspective extension: " + //$NON-NLS-1$
							element.getDeclaringExtension().getUniqueIdentifier());
				}
			}
		}
		return true;
	}

	private boolean processPerspectiveShortcut(IConfigurationElement element) {
		String id = element.getAttribute(IWorkbenchRegistryConstants.ATT_ID);
		if (id != null) {
