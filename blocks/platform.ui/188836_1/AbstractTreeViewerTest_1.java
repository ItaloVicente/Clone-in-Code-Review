	public void testChildIsNotDuplicatedWhenCompareEquals() {
		fTreeViewer.setComparator(new TestLabelComparator());
		fRootElement.deleteChildren();

		TestElement child1 = fRootElement.addChild(TestModelChange.INSERT);
		child1.setLabel("1");
		TestElement child2 = fRootElement.addChild(TestModelChange.INSERT);
		child2.setLabel("1");
		TestElement child3 = fRootElement.addChild(TestModelChange.INSERT);
		child3.setLabel("0");

		fRootElement.addChild(child1, new TestModelChange(TestModelChange.INSERT, fRootElement, child1));

		Tree tree = (Tree) fTreeViewer.getControl();
		assertEquals("Same element added to parent twice.", 3, tree.getItems().length);
	}

