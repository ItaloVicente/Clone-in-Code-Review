        if (workingSetTypePage != null && page == workingSetTypePage) {
            String pageId = workingSetTypePage.getSelection();
            if (pageId != null) {
                if (workingSetEditPage == null || pageId != editPageId) {
                    WorkingSetRegistry registry = WorkbenchPlugin.getDefault()
                            .getWorkingSetRegistry();
                    workingSetEditPage = registry.getWorkingSetPage(pageId);
                    addPage(workingSetEditPage);
                    editPageId = pageId;
                }
                return workingSetEditPage;
            }
        }
        return null;
    }
