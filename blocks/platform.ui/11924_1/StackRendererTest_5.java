		CTabFolder tabFolder = (CTabFolder) partStack.getWidget();
		tabFolder.setSelection(getTabItem(part1));

		part1.getTags().add(CSSConstants.CSS_CONTENT_CHANGE_CLASS);

		assertEquals(1,
				executedMethodsListener
						.getMethodExecutionCount("setClassnameAndId(.+)"));
		assertTrue(executedMethodsListener
				.isMethodExecuted("setClassnameAndId(.+MPart.+)"));
	}

	public void testTagsChangeHandlerWhenContentChangeForPartAndItGetsActive()
			throws Exception {
		part1.getTags().add(CSSConstants.CSS_HIGHLIGHTED_CLASS);
		part1.getTags().add(CSSConstants.CSS_ACTIVE_CLASS);

		assertFalse(part1.getTags()
				.contains(CSSConstants.CSS_HIGHLIGHTED_CLASS));
		assertEquals(1,
				executedMethodsListener
						.getMethodExecutionCount("setClassnameAndId(.+)"));
		assertTrue(executedMethodsListener
				.isMethodExecuted("setClassnameAndId(.+MPart "
						+ CSSConstants.CSS_ACTIVE_CLASS + ".+)"));
	}

	public void testTagsChangeHandlerWhenNotSupportedTagModifiedEvent()
			throws Exception {
		part1.getTags().add("not supported tag");
