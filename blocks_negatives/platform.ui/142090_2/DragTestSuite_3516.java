    }

    /**
     * Return all drop targets that only apply to views, given the window being dragged from.
     *
     * @param provider
     * @return
     * @since 3.1
     */
    private TestDropLocation[] getViewDropTargets(IWorkbenchWindowProvider dragSource) {

        String resNav = IPageLayout.ID_RES_NAV;
        String probView = IPageLayout.ID_PROBLEM_VIEW;

        TestDropLocation[] targets = new TestDropLocation[] {
            new EditorAreaDropTarget(dragSource, SWT.LEFT),
            new EditorAreaDropTarget(dragSource, SWT.RIGHT),
            new EditorAreaDropTarget(dragSource, SWT.TOP),
            new EditorAreaDropTarget(dragSource, SWT.BOTTOM),

            new ViewDropTarget(dragSource, resNav, SWT.LEFT),
            new ViewDropTarget(dragSource, resNav, SWT.RIGHT),
            new ViewDropTarget(dragSource, resNav, SWT.BOTTOM),
            new ViewDropTarget(dragSource, resNav, SWT.CENTER),
            new ViewDropTarget(dragSource, resNav, SWT.TOP),

            new ViewDropTarget(dragSource, probView, SWT.LEFT),
            new ViewDropTarget(dragSource, probView, SWT.RIGHT),
            new ViewDropTarget(dragSource, probView, SWT.BOTTOM),
            new ViewDropTarget(dragSource, probView, SWT.CENTER),
            new ViewDropTarget(dragSource, probView, SWT.TOP),

            null, //new FastViewBarDropTarget(dragSource),

            new ViewTabDropTarget(dragSource, resNav),
            new ViewTabDropTarget(dragSource, probView),
            new ViewTitleDropTarget(dragSource, probView),
            };
