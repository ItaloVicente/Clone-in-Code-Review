	@Test
	public void testNonVisibleFilters() {
		_contentService.getFilterService().persistFilterActivationState();
		NavigatorFilterService filterService = new NavigatorFilterService((NavigatorContentService) _contentService);
		assertTrue(filterService.isActive(TEST_FILTER_ACTIVE_NOT_VISIBLE));
	}

