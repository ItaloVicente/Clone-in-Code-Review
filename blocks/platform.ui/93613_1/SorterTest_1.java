	@Test
	public void testComparatorAsSorter() throws Exception {

		waitForModelObjects();

		_contentService.bindExtensions(
				new String[] { TEST_CONTENT_COMPARATOR_MODEL }, false);
		_contentService.getActivationService().activateExtensions(
				new String[] { TEST_CONTENT_COMPARATOR_MODEL }, false);

		dynamicAddModelObjects();

		TreeItem[] items = _viewer.getTree().getItems();

		TreeItem addedParent = items[_projectInd].getItem(0);
		assertEquals("BlueAddedParent", addedParent.getText());
		assertEquals("BlueAddedChild1", addedParent.getItem(0).getText());
		assertEquals("BlueChild1", addedParent.getItem(1).getText());
		assertEquals("BlueAddedFile1.txt", addedParent.getItem(2).getText());
		assertEquals("BlueAddedFile2.txt", addedParent.getItem(3).getText());

		INavigatorContentDescriptor desc = _contentService.getContentDescriptorById(TEST_CONTENT_COMPARATOR_MODEL);

		ViewerSorter sorter = _contentService.getSorterService().findSorter(desc, _project, null, null);
		assertNotNull(sorter);
		WrappedViewerComparator wrapper = (WrappedViewerComparator) sorter;
		TestComparatorData original = (TestComparatorData) wrapper.getWrappedComparator();
		Object[] dataArray = new Object[items.length];

		for (int i = 0; i < items.length; i++) {
			TreeItem treeItem = items[i];
			Object data = treeItem.getData();
			dataArray[i] = data;
			assertEquals(original.category(data), wrapper.category(data));
			assertEquals(original.isSorterProperty(data, "true"), wrapper.isSorterProperty(data, "true"));
			assertEquals(original.isSorterProperty(data, "false"), wrapper.isSorterProperty(data, "false"));
			assertEquals(original.compare(_viewer, data, items[0].getData()),
					wrapper.compare(_viewer, data, items[0].getData()));
			assertEquals(false, wrapper.isSorterProperty(data, "false"));
			assertEquals(true, wrapper.isSorterProperty(data, "true"));
		}

		Object[] copy1 = Arrays.copyOf(dataArray, dataArray.length);
		Object[] copy2 = Arrays.copyOf(dataArray, dataArray.length);
		original._forward = !original._forward;
		original.sort(_viewer, copy1);
		wrapper.sort(_viewer, copy2);
		assertArrayEquals(copy1, copy2);

		assertNotEquals(copy1[0], dataArray[0]);
	}

