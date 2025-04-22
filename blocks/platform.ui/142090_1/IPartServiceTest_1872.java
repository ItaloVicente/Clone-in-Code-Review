				super.partVisible(ref);
				assertEquals(MockViewPart.ID, ref.getId());
				assertNotNull(fPage.findView(MockViewPart.ID));
				eventReceived[0] = true;
			}
		};
		MockViewPart view = (MockViewPart) fPage.showView(MockViewPart.ID);
		MockViewPart view2 = (MockViewPart) fPage.showView(MockViewPart.ID2);
		assertEquals(view2, fPage.getActivePart());
		fPage.addPartListener(listener);
		clearEventState();
		fPage.activate(view);
		fPage.removePartListener(listener);
		assertTrue(eventReceived[0]);
	}
