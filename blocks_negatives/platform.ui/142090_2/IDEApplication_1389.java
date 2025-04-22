        Location instanceLoc = Platform.getInstanceLocation();
        if (instanceLoc == null) {
            MessageDialog
                    .openError(
                            shell,
                            IDEWorkbenchMessages.IDEApplication_workspaceMandatoryTitle,
                            IDEWorkbenchMessages.IDEApplication_workspaceMandatoryMessage);
            return EXIT_OK;
        }

        if (instanceLoc.isSet()) {
            if (!checkValidWorkspace(shell, instanceLoc.getURL())) {
