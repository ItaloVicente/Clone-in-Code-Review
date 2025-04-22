            }
         }

        return dialog;
    }

    public void testWorkbenchPref() {
        Dialog dialog = getPreferenceDialog("org.eclipse.ui.preferencePages.Workbench");
        DialogCheck.assertDialog(dialog);
    }

    public void testAppearancePref() {
        Dialog dialog = getPreferenceDialog("org.eclipse.ui.preferencePages.Views");
        DialogCheck.assertDialog(dialog);
    }

    public void testDefaultTextEditorPref() {
        Dialog dialog = getPreferenceDialog("org.eclipse.ui.preferencePages.TextEditor");
        DialogCheck.assertDialog(dialog);
    }

    public void testFileEditorsPref() {
        Dialog dialog = getPreferenceDialog("org.eclipse.ui.preferencePages.FileEditors");
        DialogCheck.assertDialog(dialog);
    }

    public void testLocalHistoryPref() {
        Dialog dialog = getPreferenceDialog("org.eclipse.ui.preferencePages.FileStates");
        DialogCheck.assertDialog(dialog);
    }

    public void testPerspectivesPref() {
        Dialog dialog = getPreferenceDialog("org.eclipse.ui.preferencePages.Perspectives");
        DialogCheck.assertDialog(dialog);
    }

    public void testInfoProp() {
        Dialog dialog = getPropertyDialog("org.eclipse.ui.propertypages.info.file");
        DialogCheck.assertDialog(dialog);
    }

    public void testProjectReferencesProp() {
        Dialog dialog = getPropertyDialog("org.eclipse.ui.propertypages.project.reference");
        DialogCheck.assertDialog(dialog);
    }
