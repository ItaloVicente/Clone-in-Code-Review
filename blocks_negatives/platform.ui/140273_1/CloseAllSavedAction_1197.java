        if (getWorkbenchWindow() == null) {
            return;
        }
        IWorkbenchPage page = getActivePage();
        if (page != null) {
            ((WorkbenchPage) page).closeAllSavedEditors();
        }
    }
