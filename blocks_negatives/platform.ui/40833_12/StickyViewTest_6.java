	/**
	 * Test that a view marked as non-closable cannot be closed as a fast view.
	 *
	 * @throws Throwable
	 * @since 3.1.1
	 */
	public void XXXtestPerspectiveCloseFastView() throws Throwable {
		page.setPerspective(WorkbenchPlugin.getDefault()
				.getPerspectiveRegistry().findPerspectiveWithId(
						PerspectiveViewsBug88345.PERSP_ID));

		try {
			IViewReference stickyRef = page
					.findViewReference(MockViewPart.IDMULT);
			IViewPart stickyView = (IViewPart) stickyRef.getPart(true);
			page.activate(stickyView);

			IViewReference viewRef = page
					.findViewReference(PerspectiveViewsBug88345.NORMAL_VIEW_ID);

			assertFalse(APITestUtils.isFastView(stickyRef));


			fail("facade.addFastView() had no implementation");

			assertTrue(APITestUtils.isFastView(stickyRef));

			fail("facade.addFastView() had no implementation");

			assertTrue(APITestUtils.isFastView(viewRef));



			fail("facade.getFVBContribution() not implemented");


		} finally {
			page.closePerspective(page.getPerspective(), false, false);
		}
	}

	/**
	 * Test that a fast view marked as non-moveable cannot be docked.
	 *
	 * @throws Throwable
	 * @since 3.1.1
	 */
	public void XXXtestPerspectiveMoveFastView() throws Throwable {
		page.setPerspective(WorkbenchPlugin.getDefault()
				.getPerspectiveRegistry().findPerspectiveWithId(
						PerspectiveViewsBug88345.PERSP_ID));

		try {
			IViewReference stickyRef = page
					.findViewReference(MockViewPart.IDMULT, "1");

			IViewReference viewRef = page
					.findViewReference(PerspectiveViewsBug88345.NORMAL_VIEW_ID);

			assertFalse(APITestUtils.isFastView(viewRef));
			assertTrue(APITestUtils.isFastView(stickyRef));

			fail("facade.addFastView() had no implementation");

			assertTrue(APITestUtils.isFastView(viewRef));


			fail("facade.addFastView() had no implementation");



		} finally {
			page.closePerspective(page.getPerspective(), false, false);
		}
	}

