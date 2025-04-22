				super.partClosed(ref);
				assertEquals(MockViewPart.ID, ref.getId());
				assertNull(fPage.findView(MockViewPart.ID));
				eventReceived[1] = true;
				assertTrue(eventReceived[0]);

			}
		};
		MockViewPart view = (MockViewPart) fPage.showView(MockViewPart.ID);
		assertEquals(view, fPage.getActivePart());
		fPage.addPartListener(listener);
		clearEventState();
		fPage.hideView(view);
		fPage.removePartListener(listener);
		history.verifyOrder(new String[] {"partHidden", "partClosed"});
		assertTrue(eventReceived[0]);
		assertTrue(eventReceived[1]);
	}

	public void testPartVisibleWhenObscured() throws Throwable {
		final boolean[] eventReceived = { false };
		IPartListener2 listener = new TestPartListener2() {
			@Override
