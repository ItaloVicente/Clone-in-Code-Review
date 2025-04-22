        if (workbenchWindow == null) {
            return;
        }
        IWorkbenchPage page = workbenchWindow.getActivePage();
        if (page != null && page.getPerspective() != null) {
            run(page, page.getPerspective());
        }
    }
