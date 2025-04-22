	public void testSorterSortOnlyMultiContent() throws Exception {

		waitForModelObjects();

		_contentService.bindExtensions(new String[] { TEST_CONTENT_SORTER_BASIC_SORTONLY_SORTER }, false);
		_contentService.getActivationService().activateExtensions(
				new String[] { TEST_CONTENT_SORTER_BASIC_SORTONLY_SORTER }, false);
		_contentService.bindExtensions(new String[] { TEST_CONTENT_SORTER_BASIC_A }, false);
		_contentService.getActivationService().activateExtensions(new String[] { TEST_CONTENT_SORTER_BASIC_A }, false);
		_contentService.bindExtensions(new String[] { TEST_CONTENT_SORTER_BASIC_B }, false);
		_contentService.getActivationService().activateExtensions(new String[] { TEST_CONTENT_SORTER_BASIC_B }, false);


		TreeItem[] items = _viewer.getTree().getItems();

		assertEquals("child1", items[0].getText());
		assertEquals("child2", items[1].getText());
		assertEquals("child3", items[2].getText());
		assertEquals("child4", items[3].getText());

	}

