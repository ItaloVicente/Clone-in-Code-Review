		};

		getViewSite().getActionBars().setGlobalActionHandler(
				ActionFactory.PASTE.getId(), pasteAction);

	}

	/**
	 * @return the preferences
	 */
	protected static IEclipsePreferences getPrefs() {
		return new InstanceScope().getNode(Activator.getPluginId());
