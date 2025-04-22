     * Adds the perspective actions to the specified menu.
     */
    private void addPerspectiveActions(MenuManager menu) {
        {
            String openText = IDEWorkbenchMessages.Workbench_openPerspective;
            MenuManager changePerspMenuMgr = new MenuManager(openText,
            IContributionItem changePerspMenuItem = ContributionItemFactory.PERSPECTIVES_SHORTLIST
                    .create(getWindow());
            changePerspMenuMgr.add(changePerspMenuItem);
            menu.add(changePerspMenuMgr);
        }
        {
            MenuManager showViewMenuMgr = new MenuManager(IDEWorkbenchMessages.Workbench_showView, "showView"); //$NON-NLS-1$
            IContributionItem showViewMenu = ContributionItemFactory.VIEWS_SHORTLIST
                    .create(getWindow());
            showViewMenuMgr.add(showViewMenu);
            menu.add(showViewMenuMgr);
        }
        menu.add(new Separator());
        menu.add(editActionSetAction);
        menu.add(getSavePerspectiveItem());
        menu.add(getResetPerspectiveItem());
        menu.add(closePerspAction);
        menu.add(closeAllPerspsAction);
    }
