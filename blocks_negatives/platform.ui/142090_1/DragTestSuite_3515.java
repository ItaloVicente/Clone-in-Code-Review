    public DragTestSuite() {
        super(Platform.find(TestPlugin.getDefault().getBundle(), new Path("data/dragtests.xml")));

        String resNav = IPageLayout.ID_RES_NAV;
        String probView = IPageLayout.ID_PROBLEM_VIEW;

        TestDragSource[] viewDragSources = new TestDragSource[] {
                new ViewDragSource(resNav, false),
                new ViewDragSource(resNav, true),
                new ViewDragSource(probView, false),
                new ViewDragSource(probView, true) };

        TestDragSource[] editorDragSources = new TestDragSource[] {
                new EditorDragSource(0, false), new EditorDragSource(0, true),
                new EditorDragSource(2, false), new EditorDragSource(2, true) };


        TestDragSource[] maximizedViewDragSources = new TestDragSource[] {
                new ViewDragSource(resNav, false, true),
                new ViewDragSource(resNav, true, true),
                new ViewDragSource(probView, false, true),
                new ViewDragSource(probView, true, true) };

        for (TestDragSource source : maximizedViewDragSources) {
            addAllCombinations(source, getMaximizedViewDropTargets(source));
        }

        for (TestDragSource source : viewDragSources) {
            addAllCombinations(source, getViewDropTargets(source));
            addAllCombinations(source, getCommonDropTargets(source));

            addAllCombinationsDetached(source, getDetachedWindowDropTargets(source));
        }

        for (TestDragSource source : editorDragSources) {
            addAllCombinations(source, getEditorDropTargets(source));
            addAllCombinations(source, getCommonDropTargets(source));

            addAllCombinationsDetached(source, getDetachedWindowDropTargets(source));
        }
        addTest(new TestSuite(Bug87211Test.class));
    }

    /**
     * Returns drop targets that will only be tested for maximized views. (we only need to ensure
     * that the view will become un-maximized -- the regular view test cases will excercise
     * the remainder of the view dragging code). We need to drag each kind of maximized view
     * to something that couldn't be seen while the view is maximized -- like the editor area).
     *
     * @param dragSource
     * @return
     * @since 3.1
     */
    private TestDropLocation[] getMaximizedViewDropTargets(IWorkbenchWindowProvider originatingWindow) {
        return new TestDropLocation[] {
                new EditorAreaDropTarget(originatingWindow, SWT.RIGHT) };
    }

    private TestDropLocation[] getCommonDropTargets(IWorkbenchWindowProvider dragSource) {
        TestDropLocation[] targets = {
            new WindowDropTarget(dragSource, SWT.TOP),
            new WindowDropTarget(dragSource, SWT.BOTTOM),
            new WindowDropTarget(dragSource, SWT.LEFT),
            new WindowDropTarget(dragSource, SWT.RIGHT) };
