        super.createControl(parent);

        PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(),
                IReadmeConstants.CONTENT_OUTLINE_PAGE_CONTEXT);

        TreeViewer viewer = getTreeViewer();
        viewer.setContentProvider(new WorkbenchContentProvider());
        viewer.setLabelProvider(new WorkbenchLabelProvider());
        viewer.setInput(getContentOutline(input));
        initDragAndDrop();

        menuMgr.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
        menuMgr.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS

        Menu menu = menuMgr.createContextMenu(viewer.getTree());
        viewer.getTree().setMenu(menu);
        getSite().registerContextMenu(
                "org.eclipse.ui.examples.readmetool.outline", menuMgr, viewer); //$NON-NLS-1$

        getSite().getActionBars().setGlobalActionHandler(
                IReadmeConstants.RETARGET2,

        OutlineAction action = new OutlineAction(MessageUtil
        getSite().getActionBars().setGlobalActionHandler(
                IReadmeConstants.LABELRETARGET3, action);
        getSite().getActionBars().setGlobalActionHandler(
                IReadmeConstants.ACTION_SET_RETARGET4, action);
        getSite().getActionBars().setGlobalActionHandler(
                IReadmeConstants.ACTION_SET_LABELRETARGET5, action);
    }

    /**
     * Gets the content outline for a given input element.
     * Returns the outline (a list of MarkElements), or null
     * if the outline could not be generated.
     */
    private IAdaptable getContentOutline(IAdaptable input) {
        return ReadmeModelFactory.getInstance().getContentOutline(input);
    }

    /**
     * Initializes drag and drop for this content outline page.
     */
    private void initDragAndDrop() {
        int ops = DND.DROP_COPY | DND.DROP_MOVE;
        Transfer[] transfers = new Transfer[] { TextTransfer.getInstance(),
                PluginTransfer.getInstance() };
        getTreeViewer().addDragSupport(ops, transfers,
                new ReadmeContentOutlineDragListener(this));
    }

    /**
     * Forces the page to update its contents.
     *
     * @see ReadmeEditor#doSave(IProgressMonitor)
     */
    public void update() {
        getControl().setRedraw(false);
        getTreeViewer().setInput(getContentOutline(input));
        getTreeViewer().expandAll();
        getControl().setRedraw(true);
    }
