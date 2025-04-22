    private IWorkbenchWindow window;

    private IWorkbenchPage page;

    /**
     * @param testName
     */
    public StickyViewTest(String testName) {
        super(testName);
    }

    public void testStackPlacementRight() {
        testStackPlacement("Right");
    }

    public void testStackPlacementLeft() {
        testStackPlacement("Left");
    }

    public void testStackPlacementTop() {
        testStackPlacement("Top");
    }

    public void testStackPlacementBottom() {
        testStackPlacement("Bottom");
    }

    /**
     * Tests to ensure that sticky views are opened in the same stack.
     */
    private void testStackPlacement(String location) {
        try {
            IViewPart part1 = page
                    .showView("org.eclipse.ui.tests.api.StickyView" + location
                            + "1");
            assertNotNull(part1);
            IViewPart part2 = page
                    .showView("org.eclipse.ui.tests.api.StickyView" + location
                            + "2");
            assertNotNull(part2);
            IViewPart[] stack = page.getViewStack(part1);

            assertTrue(ViewUtils.findInStack(stack, part1));
            assertTrue(ViewUtils.findInStack(stack, part2));

        } catch (PartInitException e) {
            fail(e.getMessage());
        }

    }

    /**
     * Tests to ensure that all views in a stack with a known sticky view are also sticky.
     */
    public void testStackContents() {
        try {
            IViewPart part1 = page
                    .showView("org.eclipse.ui.tests.api.StickyViewRight1");
            assertNotNull(part1);

            IViewPart[] stack = page.getViewStack(part1);

            for (IViewPart element : stack) {
                assertTrue(element.getTitle(), ViewUtils.isSticky(element));
            }
        } catch (PartInitException e) {
            fail(e.getMessage());
        }
    }

    /**
     * Tests whether the moveable flag is being picked up and honoured
     * from the XML.
     */
    public void XXXtestClosableFlag() {
        testCloseable("org.eclipse.ui.tests.api.StickyViewRight1", true);
        testCloseable("org.eclipse.ui.tests.api.StickyViewRight2", false);
        testCloseable("org.eclipse.ui.tests.api.StickyViewLeft1", true);
    }

    public void XXXtestMoveableFlag() {
        testMoveable("org.eclipse.ui.tests.api.StickyViewRight1", true);
        testMoveable("org.eclipse.ui.tests.api.StickyViewRight2", false);
        testMoveable("org.eclipse.ui.tests.api.StickyViewLeft1", true);
    }

    /**
     * Tests whether a sticky view with the given id is moveable or not.
     *
     * @param id the id
     * @param expectation the expected moveable state
     */
    private void testMoveable(String id, boolean expectation) {
        try {
            IViewPart part = page.showView(id);
            assertNotNull(part);
            assertTrue(ViewUtils.isSticky(part));

            IStickyViewDescriptor[] descs = PlatformUI.getWorkbench()
                    .getViewRegistry().getStickyViews();
            for (IStickyViewDescriptor desc : descs) {
                if (desc.getId().equals(id)) {
                    assertEquals(expectation, desc.isMoveable());
                }
            }

            assertEquals(expectation, ViewUtils.isMoveable(part));
        } catch (PartInitException e) {
            fail(e.getMessage());
        }
    }

    /**
     * Tests whether a sticky view with the given id is closeable or not.
     *
     * @param id the id
     * @param expectation the expected closeable state
     */
    private void testCloseable(String id, boolean expectation) {
        try {
            IViewPart part = page.showView(id);
            assertNotNull(part);
            assertTrue(ViewUtils.isSticky(part));

            IStickyViewDescriptor[] descs = PlatformUI.getWorkbench()
                    .getViewRegistry().getStickyViews();
            for (IStickyViewDescriptor desc : descs) {
                if (desc.getId().equals(id)) {
                    assertEquals(expectation, desc.isCloseable());
                }
            }

            assertEquals(expectation, ViewUtils.isCloseable(part));
        } catch (PartInitException e) {
            fail(e.getMessage());
        }
    }

    /**
     * Sticky views should remain after perspective reset.
     */
    public void testPerspectiveReset() {
        try {
            page.showView("org.eclipse.ui.tests.api.StickyViewRight1");
            page.resetPerspective();
            assertNotNull(page
                    .findView("org.eclipse.ui.tests.api.StickyViewRight1"));
        } catch (PartInitException e) {
            fail(e.getMessage());
        }
    }

    /**
     * Tests that a sticky view is opened in successive perspectives.
     */
    public void testPerspectiveOpen() {
        try {
            page.showView("org.eclipse.ui.tests.api.StickyViewRight1");
            page.setPerspective(WorkbenchPlugin.getDefault()
                    .getPerspectiveRegistry().findPerspectiveWithId(
                            "org.eclipse.ui.tests.api.SessionPerspective"));
            assertNotNull(page
                    .findView("org.eclipse.ui.tests.api.StickyViewRight1"));
        } catch (PartInitException e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test that closing a stand-alone view remove the editor stack and
     * doesn't throw an NPE.
     *
     * @throws Throwable on error
     * @since 3.2
     */
    public void testPerspectiveCloseStandaloneView() throws Throwable {
