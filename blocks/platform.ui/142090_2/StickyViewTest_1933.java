	private IWorkbenchWindow window;

	private IWorkbenchPage page;

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

	public void testPerspectiveCloseStandaloneView() throws Throwable {
