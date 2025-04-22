	@Test
	@Ignore("This does not work as expected.  See bug 93784.")
	public void testViewFoundWhenOpened() throws Throwable {
		final String viewId = MockViewPart.ID;
		final boolean[] eventReceived = { false, false };
		IPartListener listener = new TestPartListener() {
			@Override
			public void partOpened(IWorkbenchPart part) {
				super.partOpened(part);
				assertEquals(viewId, part.getSite().getId());
				assertNotNull(fPage.findView(viewId));
				IViewPart[] views = fPage.getViews();
				assertEquals(1, views.length);
				assertEquals(viewId, views[0].getSite().getId());
				eventReceived[0] = true;
			}
		};
		IPartListener2 listener2 = new TestPartListener2() {
			@Override
			public void partOpened(IWorkbenchPartReference ref) {
				super.partOpened(ref);
				assertEquals(viewId, ref.getId());
				assertNotNull(fPage.findViewReference(viewId));
				IViewReference[] refs = fPage.getViewReferences();
				assertEquals(1, refs.length);
				assertEquals(viewId, refs[0].getId());
				eventReceived[1] = true;
			}
		};
		fPage.addPartListener(listener);
		fPage.addPartListener(listener2);
		fPage.showView(viewId);
		fPage.removePartListener(listener);
		fPage.removePartListener(listener2);
		assertTrue(eventReceived[0]);
		assertTrue(eventReceived[1]);
	}
