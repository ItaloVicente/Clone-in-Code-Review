	public void testTagsChangeHandlerWhenBusyTagAddEvent() throws Exception {
		part.getTags().add(CSSConstants.CSS_BUSY_CLASS);

		assertEquals(1,
				executedMethodsListener
						.getMethodExecutionCount("setClassnameAndId(.+)"));
		assertTrue(executedMethodsListener
				.isMethodExecuted("setClassnameAndId(.+MPart "
						+ CSSConstants.CSS_BUSY_CLASS + ".+)"));
	}

	public void testTagsChangeHandlerWhenBusyTagRemoveEvent() throws Exception {
		part.getTags().add(CSSConstants.CSS_BUSY_CLASS);
		part.getTags().remove(CSSConstants.CSS_BUSY_CLASS);

		assertEquals(2,
				executedMethodsListener
						.getMethodExecutionCount("setClassnameAndId(.+)"));
		assertTrue(executedMethodsListener
				.isMethodExecuted("setClassnameAndId(.+MPart "
						+ CSSConstants.CSS_BUSY_CLASS + ".+)"));
		assertTrue(executedMethodsListener
				.isMethodExecuted("setClassnameAndId(.+MPart.+)"));
	}

	public void testTagsChangeHandlerWhenNotBusyTagModifiedEvent()
