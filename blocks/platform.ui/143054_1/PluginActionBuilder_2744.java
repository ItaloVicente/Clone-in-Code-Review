			IMenuManager parent = menu;
			if (mpath != null) {
				parent = parent.findMenuUsingPath(mpath);
				if (parent == null) {
					ideLog("Plug-in '" + ad.getPluginId() + "' contributed an invalid Menu Extension (Path: '" + mpath //$NON-NLS-1$ //$NON-NLS-2$
							+ "' is invalid): " + ad.getId()); //$NON-NLS-1$
					return;
				}
			}

			if (mgroup == null) {
