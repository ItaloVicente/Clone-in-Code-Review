		}
		prefs.putBoolean(PartRenderingEngine.DISABLED_THEME_KEY, themingDisabled.getSelection());
		try {
			prefs.flush();
		} catch (BackingStoreException e) {
			WorkbenchPlugin.log("Failed to set SWT renderer preferences", e); //$NON-NLS-1$
