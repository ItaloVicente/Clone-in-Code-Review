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

		viewer.removeAtPosition(testElement3, 4);
		printTable("After remove index 4", viewer.getTable());

		assertThat(viewer.getTable().getSelectionIndices(), is(new int[] { 0, 1, 2 }));
		IStructuredSelection structuredSelection = viewer.getStructuredSelection();
		List<TestElement> list = structuredSelection.toList();
		assertThat(list, is(selectedElementsAfterRemove));
	}

	private void printTable(String string, Table table) {
		boolean debug = false;
		if (!debug) {
			return;
		}
		System.out.println(string);
		int[] selectionIndices = table.getSelectionIndices();
		TableItem[] items = table.getItems();
		for (int i = 0; i < items.length; i++) {
			boolean printed = false;
			for (int selectedIndex : selectionIndices) {
				if (selectedIndex == i) {
					System.out.println(i + ": [" + items[i].getText() + "]");
					printed = true;
				}
			}
			if (!printed) {
				System.out.println(i + ":  " + items[i].getText());
			}
		}
	}

	private void prepRemoveAtPositionTest() {
		testElement1 = new TestElement(fModel, fRootElement);
		testElement2 = new TestElement(fModel, fRootElement);
		testElement3 = new TestElement(fModel, fRootElement);

		testElement1.fId = "1";
		testElement1.fSomeName = "Egg";
		testElement2.fId = "2";
		testElement2.fSomeName = "Tee";
		testElement3.fId = "3";
		testElement3.fSomeName = "Flower";

		TestElement[] children = new TestElement[] { testElement1, testElement1, testElement2, testElement1,
				testElement3 };
		TestModelChange testModelChange = new TestModelChange(TestModelChange.INSERT, fRootElement, children);
		fRootElement.deleteChildren();
		fRootElement.addChildren(children, testModelChange);

		Table table = (Table) fViewer.getControl();
		table.select(new int[] { 0, 1, 2, 4 });
		IStructuredSelection structuredSelection = fViewer.getStructuredSelection();
		List<TestElement> list = structuredSelection.toList();
		assertThat(list, is(Arrays.asList(testElement1, testElement1, testElement2, testElement3)));

		printTable("Before remove", ((TableViewer) fViewer).getTable());
		fViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				if (expectedSelection != null && event.getSelection().equals(expectedSelection)) {
					return;
				}
				fail();
			}
		});
		fViewer.addPostSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				if (expectedPostSelection != null && event.getSelection().equals(expectedPostSelection)) {
					return;
				}
				fail();
			}
		});
	}
