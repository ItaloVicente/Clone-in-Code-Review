				super.partVisible(ref);
				assertEquals(MockViewPart.ID, ref.getId());
				assertNotNull(fPage.findView(MockViewPart.ID));
				eventReceived[0] = true;
			}
		};
		fPage.addPartListener(listener);
		clearEventState();
		MockViewPart view = (MockViewPart) fPage.showView(MockViewPart.ID);
		fPage.removePartListener(listener);
		assertEquals(view, fPage.getActivePart());
		assertTrue(eventReceived[0]);
	}

	public void testPartVisibleWhenOpenedShared() throws Throwable {
		final boolean[] eventReceived = { false };
		IPartListener2 listener = new TestPartListener2() {
			@Override
