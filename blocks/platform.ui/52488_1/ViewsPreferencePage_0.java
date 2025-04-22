		}
		prefs.putBoolean(PartRenderingEngine.ENABLED_THEME_KEY, themingEnabled.getSelection());
		try {
			prefs.flush();
		} catch (BackingStoreException e) {
			WorkbenchPlugin.log("Failed to set SWT renderer preferences", e); //$NON-NLS-1$
