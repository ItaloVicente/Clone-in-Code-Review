	@Test
	@Ignore("Commented out until the (possible) ambiguity in bug 91775 is resolved")
	public void testCreateViewAndMakeVisibleInZoomedStack() {
		zoom(stackedView1);
		IViewPart newPart = showRegularView(ZoomPerspectiveFactory.STACK1_PLACEHOLDER1, IWorkbenchPage.VIEW_VISIBLE);

		Assert.assertTrue(page.getActivePart() == newPart);
		Assert.assertTrue(isZoomed(newPart));
	}
