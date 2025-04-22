		viewer.removeAtPosition(testElement3, 4);
		printTable("After remove index 4", viewer.getTable());

		assertThat(viewer.getTable().getSelectionIndices(), is(new int[] { 0, 1, 2 }));
		IStructuredSelection structuredSelection = viewer.getStructuredSelection();
		List<TestElement> list = structuredSelection.toList();
		assertThat(list, is(selectedElementsAfterRemove));
