	public UIPreferencesManual(String name) {
		super(name);
	}

	public void testBrokenListenerPref() {

		PreferenceDialogWrapper dialog = null;
		PreferenceManager manager = WorkbenchPlugin.getDefault()
				.getPreferenceManager();
		if (manager != null) {
			dialog = new PreferenceDialogWrapper(getShell(), manager);
			dialog.create();

			for (Object element : manager.getElements(
					PreferenceManager.PRE_ORDER)) {
				IPreferenceNode node = (IPreferenceNode) element;
				if (node
					.getId()
					.equals(
							"org.eclipse.ui.tests.manual.BrokenUpdatePreferencePage")) {
				dialog.showPage(node);
				BrokenUpdatePreferencePage page = (BrokenUpdatePreferencePage) dialog
						.getPage(node);
				page.changeFont();
				page.changePluginPreference();
				break;
				}
			}
		}

	}
