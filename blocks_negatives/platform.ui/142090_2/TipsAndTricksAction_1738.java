        /**
         * Open the tips and trick help topic
         */
        if (feature != null) {
            final String href = feature.getTipsAndTricksHref();
            if (href != null) {
                BusyIndicator.showWhile(shell.getDisplay(), () -> workbenchWindow.getWorkbench().getHelpSystem()
