		clearEventState();
		MockViewPart view = (MockViewPart) fPage.showView(MockViewPart.ID);
		assertTrue(history.verifyOrder(new String[] { "partOpened",
				"partActivated" }));
		assertEquals(view, eventPart);
		assertTrue(history2.verifyOrder(new String[] { "partOpened",
				"partVisible", "partActivated" }));
		assertEquals(getRef(view), eventPartRef);

		clearEventState();
		fPage.hideView(view);
		assertTrue(history.verifyOrder(new String[] { "partDeactivated",
				"partClosed" }));
		assertEquals(view, eventPart);
		assertTrue(history2.verifyOrder(new String[] { "partDeactivated",
				"partHidden", "partClosed" }));
		assertEquals(getRef(view), eventPartRef);

		service.removePartListener(partListener);
		service.removePartListener(partListener2);
	}

	public void testRemovePartListenerFromPage() throws Throwable {

		fPage.addPartListener(partListener);
		fPage.addPartListener(partListener2);
		fPage.removePartListener(partListener);
		fPage.removePartListener(partListener2);

		clearEventState();
		fPage.showView(MockViewPart.ID);
		assertTrue(history.isEmpty());
		assertTrue(history2.isEmpty());
	}

	public void testRemovePartListenerFromWindow() throws Throwable {
