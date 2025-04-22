        try {
            AboutInfo feature = promptForFeature();
            if (feature != null) {
                openWelcomePage(feature);
            }
        } catch (WorkbenchException e) {
            ErrorDialog.openError(workbenchWindow.getShell(),
                    IDEWorkbenchMessages.QuickStartAction_errorDialogTitle,
                    IDEWorkbenchMessages.QuickStartAction_infoReadError,
                    e.getStatus());
        }
