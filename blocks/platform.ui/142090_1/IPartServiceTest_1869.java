				super.partVisible(ref);
				assertEquals(MockViewPart.ID, ref.getId());
				assertNotNull(fPage.findView(MockViewPart.ID));
				eventReceived[0] = true;
			}
		};
		MockViewPart view = (MockViewPart) fPage.showView(MockViewPart.ID);
		IPerspectiveDescriptor emptyPerspDesc2 = fWindow.getWorkbench()
				.getPerspectiveRegistry().findPerspectiveWithId(
						EmptyPerspective.PERSP_ID2);
		fPage.setPerspective(emptyPerspDesc2);
		fPage.addPartListener(listener);
		clearEventState();
		MockViewPart view2 = (MockViewPart) fPage.showView(MockViewPart.ID);
		assertTrue(view == view2);
		assertEquals(view2, fPage.getActivePart());
		assertTrue(eventReceived[0]);
		fPage.removePartListener(listener);
	}

	public void testPartHiddenBeforeClosing() throws Throwable {

		final boolean[] eventReceived = {false, false};
		IPartListener2 listener = new TestPartListener2() {
			@Override
