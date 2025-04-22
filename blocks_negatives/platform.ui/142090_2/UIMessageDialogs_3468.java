        DialogCheck.assertDialog(dialog);
    }

    public void testWizardClosing() {
        Dialog dialog = new MessageDialog(getShell(), JFaceResources
                .getString("WizardClosingDialog.title"), null, JFaceResources
                .getString("WizardClosingDialog.message"),
                MessageDialog.QUESTION, 0,
