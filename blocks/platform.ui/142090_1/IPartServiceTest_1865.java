				super.partHidden(ref);
				assertEquals(MockViewPart.ID, ref.getId());
				assertNull(fPage.findView(MockViewPart.ID));
			}
		};
		MockViewPart view = (MockViewPart) fPage.showView(MockViewPart.ID);
		fPage.addPartListener(listener);
		clearEventState();
		fPage.hideView(view);
		assertTrue(history2.contains("partHidden"));
		assertEquals(getRef(view), eventPartRef);
		fPage.removePartListener(listener);
	}

	public void XXXtestPartHiddenWhenClosedAndShared() throws Throwable {
		IPartListener2 listener = new TestPartListener2() {
			@Override
