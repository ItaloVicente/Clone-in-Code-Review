					WorkbenchPlugin.log("Plugin \'" //$NON-NLS-1$
							+ menuElement.getContributor().getName() + "\' invalid Menu Extension (Group \'" //$NON-NLS-1$
							+ group + "\' is invalid): " + id); //$NON-NLS-1$
					return;
				}
			}

			IMenuManager newMenu = parent.findMenuUsingPath(id);
			if (newMenu == null) {
