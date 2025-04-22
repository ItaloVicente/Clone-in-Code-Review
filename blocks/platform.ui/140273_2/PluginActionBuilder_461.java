			IContributionItem sep = null;
			sep = toolbar.find(tgroup);
			if (sep == null) {
				if (appendIfMissing) {
					addGroup(toolbar, tgroup);
				} else {
					WorkbenchPlugin.log("Plug-in '" + ad.getPluginId() //$NON-NLS-1$
							+ "' invalid Toolbar Extension (Group \'" //$NON-NLS-1$
							+ tgroup + "\' is invalid): " + ad.getId()); //$NON-NLS-1$
					return;
				}
			}
			try {
				insertAfter(toolbar, tgroup, ad);
			} catch (IllegalArgumentException e) {
				WorkbenchPlugin.log("Plug-in '" + ad.getPluginId() //$NON-NLS-1$
						+ "' invalid Toolbar Extension (Group \'" //$NON-NLS-1$
						+ tgroup + "\' is missing): " + ad.getId()); //$NON-NLS-1$
			}
		}

		protected void insertMenuGroup(IMenuManager menu, AbstractGroupMarker marker) {
			menu.add(marker);
		}

		protected void insertAfter(IContributionManager mgr, String refId, ActionDescriptor desc) {
			final PluginActionContributionItem item = new PluginActionContributionItem(desc.getAction());
			item.setMode(desc.getMode());
