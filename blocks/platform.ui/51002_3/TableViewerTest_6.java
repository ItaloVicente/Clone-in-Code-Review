	}

	public void testRemoveAtPosition_selectedElement0() {
		prepRemoveAtPositionTest();

		TableViewer viewer = (TableViewer) fViewer;
		List<TestElement> selectedElementsAfterRemove = Arrays.asList(testElement1, testElement2, testElement3);
		expectedSelection = expectedPostSelection = new StructuredSelection(selectedElementsAfterRemove);

		preRemoveTableItemHook();

		viewer.removeAtPosition(testElement1, 0);
		printTable("After remove index 0", viewer.getTable());

		assertThat(viewer.getTable().getSelectionIndices(), is(new int[] { 0, 1, 3 }));
		IStructuredSelection structuredSelection = viewer.getStructuredSelection();
		List<TestElement> selectedElements = structuredSelection.toList();
		assertThat(selectedElements, is(selectedElementsAfterRemove));
	}

	public void testRemoveAtPosition_selectedElement1() {
		prepRemoveAtPositionTest();

		TableViewer viewer = (TableViewer) fViewer;
		List<TestElement> selectedElementsAfterRemove = Arrays.asList(testElement1, testElement2, testElement3);
		expectedSelection = expectedPostSelection = new StructuredSelection(selectedElementsAfterRemove);

		preRemoveTableItemHook();

		viewer.removeAtPosition(testElement1, 1);
		printTable("After remove index 1", viewer.getTable());

		assertThat(viewer.getTable().getSelectionIndices(), is(new int[] { 0, 1, 3 }));
		IStructuredSelection structuredSelection = viewer.getStructuredSelection();
		List<TestElement> list = structuredSelection.toList();
		assertThat(list, is(selectedElementsAfterRemove));

	}

	public void testRemoveAtPosition_selectedElement2() {
		prepRemoveAtPositionTest();

		TableViewer viewer = (TableViewer) fViewer;
		List<TestElement> selectedElementsAfterRemove = Arrays.asList(testElement1, testElement1, testElement3);
		expectedSelection = expectedPostSelection = new StructuredSelection(selectedElementsAfterRemove);

		preRemoveTableItemHook();

		viewer.removeAtPosition(testElement2, 2);
		printTable("After remove index 2", viewer.getTable());

		assertThat(viewer.getTable().getSelectionIndices(), is(new int[] { 0, 1, 3 }));
		IStructuredSelection structuredSelection = viewer.getStructuredSelection();
		List<TestElement> list = structuredSelection.toList();
		assertThat(list, is(selectedElementsAfterRemove));
	}

	public void testRemoveAtPosition_notSelectedElement() {
		prepRemoveAtPositionTest();

		TableViewer viewer = (TableViewer) fViewer;
		List<TestElement> selectedElementsAfterRemove = Arrays.asList(testElement1, testElement1, testElement2,
				testElement3);

		preRemoveTableItemHook();

		viewer.removeAtPosition(testElement1, 3);
		printTable("After remove index 3", viewer.getTable());

		assertThat(viewer.getTable().getSelectionIndices(), is(new int[] { 0, 1, 2, 3 }));
		IStructuredSelection structuredSelection = viewer.getStructuredSelection();
		List<TestElement> selectedElements = structuredSelection.toList();
		assertThat(selectedElements, is(selectedElementsAfterRemove));
	}

	public void testRemoveAtPosition_selectedElement3() {
		prepRemoveAtPositionTest();

		TableViewer viewer = (TableViewer) fViewer;
		List<TestElement> selectedElementsAfterRemove = Arrays.asList(testElement1, testElement1, testElement2);
		expectedSelection = expectedPostSelection = new StructuredSelection(selectedElementsAfterRemove);

		preRemoveTableItemHook();
