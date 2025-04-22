        fPage.showActionSet("org.eclipse.ui.tests.internal.ListElementActions");
        WorkbenchWindow win = (WorkbenchWindow) fWindow;
        IContributionItem item = win.getMenuBarManager().find(
                "org.eclipse.ui.tests.internal.ListElementMenu");
        while (item instanceof SubContributionItem) {
            item = ((SubContributionItem) item).getInnerItem();
            if (item instanceof MenuManager) {
