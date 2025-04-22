		viewer.removeAtPosition(testElement1, 0);
		printTable("After remove index 0", viewer.getTable());

		assertThat(viewer.getTable().getSelectionIndices(), is(new int[] { 0, 1, 3 }));
		IStructuredSelection structuredSelection = viewer.getStructuredSelection();
		List<TestElement> selectedElements = structuredSelection.toList();
		assertThat(selectedElements, is(selectedElementsAfterRemove));
