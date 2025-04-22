	/**
	 * Tests bug 425525 using a view that contributes to the 'Properties' view
	 * without using a customized page. This test uses the 'Navigator' view to
	 * achieve this.
	 *
	 * @see #testBug425525(String, boolean)
	 */
	@Test
	public void testInitialSelectionWithNormalProperties() throws Exception {
		testBug425525(IPageLayout.ID_RES_NAV, true);
	}

