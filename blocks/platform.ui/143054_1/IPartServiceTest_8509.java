				super.partHidden(ref);
				assertEquals(MockViewPart.ID, ref.getId());
				assertNotNull(fPage.findView(MockViewPart.ID));
				eventReceived[0] = true;
			}
		};
		MockViewPart view2 = (MockViewPart) fPage.showView(MockViewPart.ID2);
		MockViewPart view = (MockViewPart) fPage.showView(MockViewPart.ID);
		assertEquals(view, fPage.getActivePart());
		fPage.addPartListener(listener);
		clearEventState();
		fPage.activate(view2);
		fPage.removePartListener(listener);
		assertTrue(eventReceived[0]);
	}

	public void testPartVisibleWhenOpenedUnshared() throws Throwable {
		final boolean[] eventReceived = { false };
		IPartListener2 listener = new TestPartListener2() {
			@Override
