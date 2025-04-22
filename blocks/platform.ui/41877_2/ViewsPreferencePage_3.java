	protected void createEnableMruPref(Composite composite) {
		IEclipsePreferences prefs = getSwtRendererPreferences();
		boolean mruControlledByCSS = prefs.getBoolean(StackRenderer.MRU_CONTROLLED_BY_CSS_KEY, false);
		if (mruControlledByCSS) {
			return;
		}
		boolean defaultValue = getDefaultMRUValue();
		boolean actualValue = prefs.getBoolean(StackRenderer.MRU_KEY, defaultValue);
		enableMru = createCheckButton(composite, WorkbenchMessages.ViewsPreference_enableMru, actualValue);
	}

