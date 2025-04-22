	public UIComparePreferencesAuto(String name) {
		super(name);
	}

	protected Shell getShell() {
		return DialogCheck.getShell();
	}

	private PreferenceDialog getPreferenceDialog(String id) {
		PreferenceDialogWrapper dialog = null;
		PreferenceManager manager = WorkbenchPlugin.getDefault()
				.getPreferenceManager();
		if (manager != null) {
			dialog = new PreferenceDialogWrapper(getShell(), manager);
			dialog.create();
			PlatformUI.getWorkbench().getHelpSystem().setHelp(dialog.getShell(),
					IWorkbenchHelpContextIds.PREFERENCE_DIALOG);

			for (Object element : manager.getElements(
					PreferenceManager.PRE_ORDER)) {
				IPreferenceNode node = (IPreferenceNode) element;
				if (node.getId().equals(id)) {
					dialog.showPage(node);
					break;
				}
			}
		}
		return dialog;
	}

	public void testCompareViewersPref() {
		Dialog dialog = getPreferenceDialog("org.eclipse.compare.internal.ComparePreferencePage");
		DialogCheck.assertDialogTexts(dialog);
	}
