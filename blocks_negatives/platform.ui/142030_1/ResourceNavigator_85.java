        memento = null;

        getSite().getWorkbenchWindow().getWorkbench().getHelpSystem().setHelp(
				viewer.getControl(), getHelpContextId());
    }

    /**
     * Returns the help context id to use for this view.
     *
     * @since 2.0
     */
    protected String getHelpContextId() {
        return INavigatorHelpContextIds.RESOURCE_VIEW;
    }

    /**
     * Initializes and registers the context menu.
     *
     * @since 2.0
     */
    protected void initContextMenu() {
        menuMgr.setRemoveAllWhenShown(true);
        menuMgr.addMenuListener(manager -> ResourceNavigator.this.fillContextMenu(manager));
        TreeViewer viewer = getTreeViewer();
        Menu menu = menuMgr.createContextMenu(viewer.getTree());
        viewer.getTree().setMenu(menu);
        getSite().registerContextMenu(menuMgr, viewer);
    }

    /**
     * Creates the viewer.
     *
     * @param parent the parent composite
     * @since 2.0
     */
    protected TreeViewer createViewer(Composite parent) {
        TreeViewer viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL
                | SWT.V_SCROLL);
        viewer.setUseHashlookup(true);
        initContentProvider(viewer);
        initLabelProvider(viewer);
        initFilters(viewer);
        initListeners(viewer);

        return viewer;
    }

    /**
     * Sets the content provider for the viewer.
     *
     * @param viewer the viewer
     * @since 2.0
     */
    protected void initContentProvider(TreeViewer viewer) {
        viewer.setContentProvider(new WorkbenchContentProvider());
    }

    /**
     * Sets the label provider for the viewer.
     *
     * @param viewer the viewer
     * @since 2.0
     */
    protected void initLabelProvider(TreeViewer viewer) {
        viewer.setLabelProvider(new DecoratingLabelProvider(
                new WorkbenchLabelProvider(), getPlugin().getWorkbench()
                        .getDecoratorManager().getLabelDecorator()));
    }

    /**
     * Adds the filters to the viewer.
     *
     * @param viewer the viewer
     * @since 2.0
     */
    protected void initFilters(TreeViewer viewer) {
        viewer.addFilter(patternFilter);
        viewer.addFilter(workingSetFilter);
    }

    /**
     * Initializes the linking enabled setting from the preference store.
     */
    private void initLinkingEnabled() {
        String setting = settings
                .get(IWorkbenchPreferenceConstants.LINK_NAVIGATOR_TO_EDITOR);
        if (setting != null) {
            return;
        }
        linkingEnabled = PlatformUI.getPreferenceStore().getBoolean(
                IWorkbenchPreferenceConstants.LINK_NAVIGATOR_TO_EDITOR);
    }

    /**
     * Adds the listeners to the viewer.
     *
     * @param viewer the viewer
     * @since 2.0
     */
    protected void initListeners(final TreeViewer viewer) {
        viewer.addSelectionChangedListener(event -> handleSelectionChanged(event));
        viewer.addDoubleClickListener(event -> handleDoubleClick(event));
