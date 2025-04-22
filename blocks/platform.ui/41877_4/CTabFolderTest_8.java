		final boolean mruControlledByCSSDefault = CSSPropertyMruVisibleSWTHandler.isMRUControlledByCSS();
		try {
			CSSPropertyMruVisibleSWTHandler.setMRUControlledByCSS(true);
			CTabFolder folderToTest = createTestCTabFolder("CTabFolder { mru-visible: true}");
			assertEquals(true, folderToTest.getMRUVisible());
			assertEquals("true", engine.retrieveCSSProperty(folderToTest, "mru-visible", null));
			folderToTest.getShell().close();
			folderToTest = createTestCTabFolder("CTabFolder { mru-visible: false}");
			assertEquals("false", engine.retrieveCSSProperty(folderToTest, "mru-visible", null));
			assertEquals(false, folderToTest.getMRUVisible());
		} finally {
			CSSPropertyMruVisibleSWTHandler.setMRUControlledByCSS(mruControlledByCSSDefault);
		}
	}

	@SuppressWarnings("restriction")
	@Test
	public void testMRUVisibleCSSControlOff() {
		final boolean mruControlledByCSSDefault = CSSPropertyMruVisibleSWTHandler.isMRUControlledByCSS();
		try {
			CSSPropertyMruVisibleSWTHandler.setMRUControlledByCSS(false);
			CTabFolder folderToTest = createTestCTabFolder("CTabFolder { mru-visible: true}");
			assertEquals(false, folderToTest.getMRUVisible());
			assertEquals("false", engine.retrieveCSSProperty(folderToTest, "mru-visible", null));
			folderToTest.getShell().close();
			folderToTest = createTestCTabFolder("CTabFolder { mru-visible: false}");
			assertEquals("false", engine.retrieveCSSProperty(folderToTest, "mru-visible", null));
			assertEquals(false, folderToTest.getMRUVisible());
		} finally {
			CSSPropertyMruVisibleSWTHandler.setMRUControlledByCSS(mruControlledByCSSDefault);
		}
