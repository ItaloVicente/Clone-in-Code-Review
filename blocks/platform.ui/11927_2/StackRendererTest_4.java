	public void testTagsChangeHandlerWhenContentChangedOfNotActivePart()
			throws Exception {
		CTabFolder tabFolder = (CTabFolder) partStack.getWidget();
		tabFolder.setSelection(getTabItem(part2));

		part1.getTags().add(CSSConstants.CSS_CONTENT_CHANGE_CLASS);

		assertEquals(1,
				executedMethodsListener
						.getMethodExecutionCount("setClassnameAndId(.+)"));
		assertTrue(executedMethodsListener
				.isMethodExecuted("setClassnameAndId(.+MPart "
						+ CSSConstants.CSS_HIGHLIGHTED_CLASS + ".+)"));
	}

	public void testTagsChangeHandlerWhenContentChangedOfSelectedPart()
