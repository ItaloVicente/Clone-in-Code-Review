					WorkbenchPlugin
							.log("Plug-in '" + ad.getPluginId() + "' contributed an invalid Menu Extension (Group: '" //$NON-NLS-1$ //$NON-NLS-2$
									+ mgroup + "' is invalid): " + ad.getId()); //$NON-NLS-1$
					return;
				}
			}

			try {
				insertAfter(parent, mgroup, ad);
			} catch (IllegalArgumentException e) {
				WorkbenchPlugin.log("Plug-in '" + ad.getPluginId() + "' contributed an invalid Menu Extension (Group: '" //$NON-NLS-1$ //$NON-NLS-2$
						+ mgroup + "' is missing): " + ad.getId()); //$NON-NLS-1$
			}
		}

		protected void contributeSeparator(IMenuManager menu, IConfigurationElement element) {
			String id = element.getAttribute(IWorkbenchRegistryConstants.ATT_NAME);
			if (id == null || id.length() <= 0) {
