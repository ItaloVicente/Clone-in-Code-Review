    }

    private TestDropLocation[] getEditorDropTargets(IWorkbenchWindowProvider originatingWindow) {
        String resNav = IPageLayout.ID_RES_NAV;
        return new TestDropLocation[] {
                new ViewDropTarget(originatingWindow, resNav, SWT.CENTER),

                new EditorDropTarget(originatingWindow, 2, SWT.LEFT),
                new EditorDropTarget(originatingWindow, 2, SWT.RIGHT),
                new EditorDropTarget(originatingWindow, 2, SWT.TOP),
                new EditorDropTarget(originatingWindow, 2, SWT.BOTTOM),
                new EditorDropTarget(originatingWindow, 2, SWT.CENTER),

                new EditorDropTarget(originatingWindow, 0, SWT.LEFT),
                new EditorDropTarget(originatingWindow, 0, SWT.RIGHT),
                new EditorDropTarget(originatingWindow, 0, SWT.BOTTOM),
                new EditorDropTarget(originatingWindow, 0, SWT.CENTER),
                new EditorTabDropTarget(originatingWindow, 0),
                new EditorTitleDropTarget(originatingWindow, 0),
                };
    }

    private void addAllCombinations(TestDragSource dragSource,
            TestDropLocation[] dropTargets) {

        for (TestDropLocation dropTarget : dropTargets) {
        	if (dropTarget == null) {
