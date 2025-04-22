			String[] files = srcFolder.list();
			if (files == null) {
				return new Status(IStatus.ERROR, PlatformUI.PLUGIN_ID,
						"Content from directory '" + srcFolder.getAbsolutePath() + "' can not be listed."); //$NON-NLS-1$ //$NON-NLS-2$
			}
			for (String file : files) {
