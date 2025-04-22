	public void testSelectionEventWhenInactive() throws Throwable {
		Object sel1 = "Selection 1", sel2 = "Selection 2";

		fPage.addSelectionListener(this);

		SelectionProviderView view1 = (SelectionProviderView) fPage
				.showView(SelectionProviderView.ID);
		SelectionProviderView view2 = (SelectionProviderView) fPage
				.showView(SelectionProviderView.ID_2);

		clearEventState();
		view2.setSelection(sel2);
		assertTrue("EventReceived", eventReceived);
		assertEquals("EventPart", view2, eventPart);
		assertEquals("Event Selection", sel2, unwrapSelection(eventSelection));

		clearEventState();
		view1.setSelection(sel1);
		assertTrue("Unexpected selection events received", !eventReceived);
	}

	public void testSelectionEventWhenActivated() throws Throwable {
		Object sel1 = "Selection 1", sel2 = "Selection 2";

		fPage.addSelectionListener(this);

		SelectionProviderView view1 = (SelectionProviderView) fPage
				.showView(SelectionProviderView.ID);
		view1.setSelection(sel1);

		SelectionProviderView view2 = (SelectionProviderView) fPage
				.showView(SelectionProviderView.ID_2);
		view2.setSelection(sel2);

		clearEventState();
		fPage.activate(view1);
		assertTrue("EventReceived", eventReceived);
		assertEquals("EventPart", view1, eventPart);
		assertEquals("Event Selection", sel1, unwrapSelection(eventSelection));

		clearEventState();
		fPage.activate(view2);
		assertTrue("EventReceived", eventReceived);
		assertEquals("EventPart", view2, eventPart);
		assertEquals("Event Selection", sel2, unwrapSelection(eventSelection));
	}

	private Object unwrapSelection(ISelection sel) {
		if (sel instanceof IStructuredSelection) {
			IStructuredSelection struct = (IStructuredSelection) sel;
			if (struct.size() == 1) {
