        IWorkbenchPage page = getSite().getPage();
        if (page != null) {
            bootstrapSelection = page.getSelection();
            return page.getActivePart();
        }
        return null;
