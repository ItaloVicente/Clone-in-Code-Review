	@Test
	@Ignore
	public void testNullListener() throws Throwable {
		INullSelectionListener nullSelectionListener = (pPart, pSelection) -> fEventReceived = true;
		SelectionProviderView view = (SelectionProviderView) fPage.showView(SelectionProviderView.ID);
		ISelectionListener listener = SelectionListenerFactory.createVisibleListener(view, nullSelectionListener,
				m -> true);
		fPage.addSelectionListener(listener);
		view.setSelection(new StructuredSelection(KNOCK_KNOCK));
