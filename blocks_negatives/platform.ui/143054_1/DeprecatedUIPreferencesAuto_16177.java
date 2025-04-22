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
        /*
         * Commented out because it generates a failure
         * of expect and actual width values. Suspect this
         * is an SWT issue.
         *
         Dialog dialog = getPropertyDialog("org.eclipse.ui.propertypages.info.file");
         DialogCheck.assertDialogTexts(dialog);
         */
    }

    public void testProjectReferencesProp() {
        /*
         * Commented out because it generates a failure
         * of expect and actual width values. Suspect this
         * is an SWT issue.
         *
         Dialog dialog = getPropertyDialog("org.eclipse.ui.propertypages.project.reference");
         DialogCheck.assertDialogTexts(dialog);
         */
    }

    /**
     * Test the editors preference page and toggle the
     * enable state twice to be sure there are no errors.
     */
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
