        }
    }

    /**
     * Creates the table control.
     */
    void createTable(Composite parent) {
        table = new Table(parent, SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI
                | SWT.FULL_SELECTION);
        table.setLinesVisible(true);
    }

    /**
     * Fills the local tool bar and menu manager with actions.
     */
    void fillActionBars() {
        IActionBars actionBars = getViewSite().getActionBars();
        IMenuManager menu = actionBars.getMenuManager();
        IMenuManager submenu = new MenuManager(BookmarkMessages.SortMenuGroup_text);
        menu.add(submenu);
        submenu.add(sortByDescriptionAction);
        submenu.add(sortByResourceAction);
        submenu.add(sortByFolderAction);
        submenu.add(sortByLineAction);
        submenu.add(sortByCreationTime);
        submenu.add(new Separator());
        submenu.add(sortAscendingAction);
        submenu.add(sortDescendingAction);
    }

    void createSortActions() {
        sortByDescriptionAction = new SortByAction(BookmarkSorter.DESCRIPTION);
        sortByDescriptionAction.setText(BookmarkMessages.ColumnDescription_text);
        PlatformUI.getWorkbench().getHelpSystem().setHelp(
