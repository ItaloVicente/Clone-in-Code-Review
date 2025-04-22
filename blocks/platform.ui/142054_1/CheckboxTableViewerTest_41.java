		Table table = new Table(parent, SWT.CHECK | SWT.BORDER);
		table.setLinesVisible(true);
		TableLayout layout = new TableLayout();
		table.setLayout(layout);
		table.setHeaderVisible(true);

		String headers[] = { "column 1 header", "column 2 header" };

		ColumnLayoutData layouts[] = { new ColumnWeightData(100), new ColumnWeightData(100) };

		final TableColumn columns[] = new TableColumn[headers.length];

		for (int i = 0; i < headers.length; i++) {
			layout.addColumnData(layouts[i]);
			TableColumn tc = new TableColumn(table, SWT.NONE, i);
			tc.setResizable(layouts[i].resizable);
			tc.setText(headers[i]);
			columns[i] = tc;
		}

		TableViewer viewer = new CheckboxTableViewer(table);
		viewer.setContentProvider(new TestModelContentProvider());
		viewer.setLabelProvider(new TableTestLabelProvider());
		return viewer;
	}

	public static void main(String args[]) {
		junit.textui.TestRunner.run(CheckboxTableViewerTest.class);
	}

	public void testCheckAllElements() {
		CheckboxTableViewer ctv = (CheckboxTableViewer) fViewer;
		ctv.setAllChecked(true);
		assertTrue(ctv.getChecked(fRootElement.getFirstChild()));
		assertTrue(ctv.getChecked(fRootElement.getLastChild()));
		ctv.setAllChecked(false);
		assertTrue(!ctv.getChecked(fRootElement.getFirstChild()));
		assertTrue(!ctv.getChecked(fRootElement.getLastChild()));
	}

	public void testGrayAllElements() {
		CheckboxTableViewer ctv = (CheckboxTableViewer) fViewer;
		ctv.setAllGrayed(true);
		assertTrue(ctv.getGrayed(fRootElement.getFirstChild()));
		assertTrue(ctv.getGrayed(fRootElement.getLastChild()));
		ctv.setAllGrayed(false);
		assertTrue(!ctv.getGrayed(fRootElement.getFirstChild()));
		assertTrue(!ctv.getGrayed(fRootElement.getLastChild()));
	}

	public void testGrayed() {
		CheckboxTableViewer ctv = (CheckboxTableViewer) fViewer;
		TestElement element = fRootElement.getFirstChild();

		assertTrue(ctv.getGrayedElements().length == 0);
		assertTrue(!ctv.getGrayed(element));

		ctv.setGrayed(element, true);
		assertTrue(ctv.getGrayedElements().length == 1);
		assertTrue(ctv.getGrayed(element));

		ctv.setGrayed(element, false);
		assertTrue(ctv.getGrayedElements().length == 0);
		assertTrue(!ctv.getGrayed(element));

		ctv.setAllGrayed(false);
	}

	public void testGrayedElements() {
		CheckboxTableViewer ctv = (CheckboxTableViewer) fViewer;
		TestElement first = fRootElement.getFirstChild();
		TestElement last = fRootElement.getLastChild();

		assertTrue(ctv.getGrayedElements().length == 0);
		assertTrue(!ctv.getGrayed(first));
		assertTrue(!ctv.getGrayed(last));

		ctv.setGrayed(first, true);
		ctv.setGrayed(last, true);
		Object[] elements = ctv.getGrayedElements();
		assertTrue(elements.length == 2);
		assertTrue(elements[0] == first);
		assertTrue(elements[1] == last);

		ctv.setGrayed(first, false);
		ctv.setGrayed(last, false);
		assertTrue(ctv.getGrayedElements().length == 0);

		ctv.setAllGrayed(false);
	}

	public void testWithoutCheckProvider() {
		CheckboxTableViewer ctv = (CheckboxTableViewer) fViewer;
		ctv.refresh();
	}

	public void testCheckProviderInvoked() {
		CheckboxTableViewer ctv = (CheckboxTableViewer) fViewer;

		TestMethodsInvokedCheckStateProvider provider = new TestMethodsInvokedCheckStateProvider();

		ctv.setCheckStateProvider(provider);
		assertTrue("isChecked should be invoked on a refresh", (!provider.isCheckedInvokedOn.isEmpty()));
		assertTrue("isGrayed should be invoked on a refresh", (!provider.isGrayedInvokedOn.isEmpty()));

		provider.reset();
		ctv.refresh();
		assertTrue("isChecked should be invoked on a refresh", (!provider.isCheckedInvokedOn.isEmpty()));
		assertTrue("isGrayed should be invoked on a refresh", (!provider.isGrayedInvokedOn.isEmpty()));
	}

	public void testCheckedFalseGrayedFalse() {
		testSpecificState(false, false);
	}

	public void testCheckedFalseGrayedTrue() {
		testSpecificState(false, true);
	}

	public void testCheckedTrueGrayedFalse() {
		testSpecificState(true, false);
	}

	public void testCheckedTrueGrayedTrue() {
		testSpecificState(true, true);
	}

	private void testSpecificState(final boolean isChecked, final boolean isGrayed) {
		CheckboxTableViewer ctv = (CheckboxTableViewer) fViewer;

		ctv.setCheckStateProvider(new ICheckStateProvider() {
