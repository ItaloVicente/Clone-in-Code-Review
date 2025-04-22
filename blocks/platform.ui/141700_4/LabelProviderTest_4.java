	@Test
	public void testLabelProviderSupportsTooltip() throws Exception {
		_contentService.bindExtensions(new String[] { TEST_CONTENT_TOOLTIPS, COMMON_NAVIGATOR_RESOURCE_EXT }, true);
		_contentService.getActivationService()
				.activateExtensions(new String[] { TEST_CONTENT_TOOLTIPS, COMMON_NAVIGATOR_RESOURCE_EXT }, true);

		refreshViewer();

		assertTrue(_viewer.getLabelProvider() instanceof IToolTipProvider);
		IToolTipProvider tooltipProvider = (IToolTipProvider) _viewer.getLabelProvider();

		assertTrue(_viewer.getContentProvider() instanceof ITreeContentProvider);
		ITreeContentProvider contentProvider = (ITreeContentProvider) _viewer.getContentProvider();

		Object[] rootElements = contentProvider.getElements(_viewer.getInput());

		for (Object element : rootElements) {
			assertTrue(tooltipProvider.getToolTipText(element).startsWith("ToolTip"));
		}
	}
