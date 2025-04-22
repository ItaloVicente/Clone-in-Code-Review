        callTrace.add("createPartControl");

        viewer = new ListViewer(parent);
        viewer.setLabelProvider(new LabelProvider());
        viewer.setContentProvider(new ListContentProvider());
        viewer.setInput(input);

        createPopupMenu();

        getSite().setSelectionProvider(viewer);
    }

    /**
     * Creates a popup menu.
     */
    public void createPopupMenu() {
        addAction = new Action("Add Standard Items") {
            @Override
