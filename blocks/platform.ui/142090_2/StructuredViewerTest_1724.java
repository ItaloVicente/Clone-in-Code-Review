		fViewer.setLabelProvider(new TestLabelProvider());
		TestElement first = fRootElement.getFirstChild();
		first.setLabel("changed name");
		String newLabel = providedString(first);
		assertEquals("rendered label", newLabel, getItemText(0));
	}

	public void testRenameWithSorter() {
		fViewer.setComparator(new TestLabelComparator());
		TestElement first = fRootElement.getFirstChild();
		first.setLabel("name-9999");
		String newElementLabel = first.toString();
		assertEquals("sorted first", newElementLabel, getItemText(0));
	}

	public void testSetInput() {
		TestElement first = fRootElement.getFirstChild();
		TestElement firstfirst = first.getFirstChild();

		fRootElement = first;
		setInput();
		processEvents();
