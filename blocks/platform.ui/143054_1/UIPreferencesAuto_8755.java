	public UIPreferencesAuto(String name) {
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


	public void testWorkbenchPref() {
		Dialog dialog = getPreferenceDialog("org.eclipse.ui.preferencePages.Workbench");
		DialogCheck.assertDialogTexts(dialog);
	}

	public void testAppearancePref() {
		Dialog dialog = getPreferenceDialog("org.eclipse.ui.preferencePages.Views");
		DialogCheck.assertDialogTexts(dialog);
	}

	public void testDefaultTextEditorPref() {
		Dialog dialog = getPreferenceDialog("org.eclipse.ui.preferencePages.TextEditor");
		DialogCheck.assertDialogTexts(dialog);
	}

	public void testFileEditorsPref() {
		Dialog dialog = getPreferenceDialog("org.eclipse.ui.preferencePages.FileEditors");
		DialogCheck.assertDialogTexts(dialog);
	}

	public void testLocalHistoryPref() {
		Dialog dialog = getPreferenceDialog("org.eclipse.ui.preferencePages.FileStates");
		DialogCheck.assertDialogTexts(dialog);
	}

	public void testPerspectivesPref() {
		Dialog dialog = getPreferenceDialog("org.eclipse.ui.preferencePages.Perspectives");
		DialogCheck.assertDialogTexts(dialog);
	}

	public void testFontEditorsPref() {
		Dialog dialog = getPreferenceDialog("org.eclipse.ui.tests.dialogs.FontFieldEditorTestPreferencePage");
		DialogCheck.assertDialogTexts(dialog);
	}

	public void testInfoProp() {
		 Dialog dialog = getPropertyDialog("org.eclipse.ui.propertypages.info.file");
		 DialogCheck.assertDialogTexts(dialog);
	}

	public void testProjectReferencesProp() {
		 Dialog dialog = getPropertyDialog("org.eclipse.ui.propertypages.project.reference");
		 DialogCheck.assertDialogTexts(dialog);
	}

	public void testFieldEditorEnablePref() {

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
							"org.eclipse.ui.tests.dialogs.EnableTestPreferencePage")) {
					dialog.showPage(node);
					EnableTestPreferencePage page = (EnableTestPreferencePage) dialog
						.getPage(node);
					page.flipState();
					page.flipState();
					break;
				}
			}
		}

	}
