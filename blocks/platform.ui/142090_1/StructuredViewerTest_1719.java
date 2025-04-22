	public void testSetAndGetData() {

		assertNull("get with no data", fViewer.getData("foo"));

		fViewer.setData("foo", null);

		assertNull("get with no data after remove", fViewer.getData("foo"));

		fViewer.setData("foo", "bar");

		fViewer.setData("baz", null);

		assertNull("get key which does not exist", fViewer.getData("baz"));

		assertNull("get value instead of key", fViewer.getData("bar"));

		assertEquals("get single value", "bar", fViewer.getData("foo"));

		fViewer.setData("foo", "baz");

		assertEquals("get overridden value", "baz", fViewer.getData("foo"));

		fViewer.setData("alpha", "1");
		fViewer.setData("beta", "2");
		fViewer.setData("delta", "3");

		assertEquals("get multiple values", "baz", fViewer.getData("foo"));
		assertEquals("get multiple values", "1", fViewer.getData("alpha"));
		assertEquals("get multiple values", "2", fViewer.getData("beta"));
		assertEquals("get multiple values", "3", fViewer.getData("delta"));

		fViewer.setData("alpha", "10");

		assertEquals("get overridden value", "10", fViewer.getData("alpha"));

		fViewer.setData("gamma", "4");
		fViewer.setData("epsilon", "5");

		fViewer.setData("foo", null);

		assertEquals("get after remove", null, fViewer.getData("foo"));
		assertEquals("get after remove", "10", fViewer.getData("alpha"));
		assertEquals("get after remove", "2", fViewer.getData("beta"));
		assertEquals("get after remove", "3", fViewer.getData("delta"));
		assertEquals("get after remove", "4", fViewer.getData("gamma"));
		assertEquals("get after remove", "5", fViewer.getData("epsilon"));

		fViewer.setData("delta", null);

		assertEquals("get after remove", null, fViewer.getData("foo"));
		assertEquals("get after remove", "10", fViewer.getData("alpha"));
		assertEquals("get after remove", "2", fViewer.getData("beta"));
		assertEquals("get after remove", null, fViewer.getData("delta"));
		assertEquals("get after remove", "4", fViewer.getData("gamma"));
		assertEquals("get after remove", "5", fViewer.getData("epsilon"));

		fViewer.setData("epsilon", null);

		assertEquals("get after remove", null, fViewer.getData("foo"));
		assertEquals("get after remove", "10", fViewer.getData("alpha"));
		assertEquals("get after remove", "2", fViewer.getData("beta"));
		assertEquals("get after remove", null, fViewer.getData("delta"));
		assertEquals("get after remove", "4", fViewer.getData("gamma"));
		assertEquals("get after remove", null, fViewer.getData("epsilon"));

		fViewer.setData("alpha", null);
		fViewer.setData("beta", null);
		fViewer.setData("gamma", null);

		assertEquals("get after remove", null, fViewer.getData("foo"));
		assertEquals("get after remove", null, fViewer.getData("alpha"));
		assertEquals("get after remove", null, fViewer.getData("beta"));
		assertEquals("get after remove", null, fViewer.getData("delta"));
		assertEquals("get after remove", null, fViewer.getData("gamma"));
		assertEquals("get after remove", null, fViewer.getData("epsilon"));
	}

	public void testInsertChild() {
		TestElement first = fRootElement.getFirstChild();
		TestElement newElement = first.addChild(TestModelChange.INSERT);
		assertNull("new sibling is not visible", fViewer
				.testFindItem(newElement));
	}

	public void testInsertSibling() {
		TestElement newElement = fRootElement.addChild(TestModelChange.INSERT);
		processEvents();
