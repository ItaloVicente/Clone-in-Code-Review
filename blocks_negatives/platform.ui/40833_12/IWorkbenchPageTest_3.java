	/**
	 * Tests showing multi-instance views (as fast views). This is a regression
	 * test for bug 76669 - [Perspectives] NullPointerException in
	 * Perspective.getFastViewWidthRatio()
	 */
	public void XXXtestShowViewMultFast() throws Throwable {
		/*
		 * javadoc: Shows the view identified by the given view id and secondary
		 * id in this page and gives it focus. This allows multiple instances of
		 * a particular view to be created. They are disambiguated using the
		 * secondary id.
		 */
		MockViewPart view = (MockViewPart) fActivePage
				.showView(MockViewPart.IDMULT);
		assertNotNull(view);
		assertTrue(view.getCallHistory().verifyOrder(
				new String[] { "init", "createPartControl", "setFocus" }));
		MockViewPart view2 = (MockViewPart) fActivePage.showView(
				MockViewPart.IDMULT, "2", IWorkbenchPage.VIEW_ACTIVATE);
		assertNotNull(view2);
		assertTrue(view2.getCallHistory().verifyOrder(
				new String[] { "init", "createPartControl", "setFocus" }));
		assertTrue(!view.equals(view2));


		fail("facade.addFastView() contained no implementation");


		fActivePage.activate(view);
		assertEquals(view, fActivePage.getActivePart());

		fActivePage.activate(view2);
		assertEquals(view2, fActivePage.getActivePart());
	}

	/**
	 * Tests saving the page state when there is a fast view that is also a
	 * multi-instance view. This is a regression test for bug 76669 -
	 * [Perspectives] NullPointerException in
	 * Perspective.getFastViewWidthRatio()
	 */
	public void XXXtestBug76669() throws Throwable {


		fail("facade.addFastView() contained no implementation");

		IMemento memento = XMLMemento.createWriteRoot("page");
		fail("facade.saveState() had no implementation");

		IMemento persps = memento.getChild("perspectives");
		IMemento persp = persps.getChildren("perspective")[0];
		IMemento[] fastViews = persp.getChild("fastViews").getChildren("view");
		assertEquals(2, fastViews.length);
	}

