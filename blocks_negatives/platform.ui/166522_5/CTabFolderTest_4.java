	@Test
	public void testMRUVisible() {
		final boolean mruControlledByCSSDefault = CSSPropertyMruVisibleSWTHandler.isMRUControlledByCSS();
		try {
			CSSPropertyMruVisibleSWTHandler.setMRUControlledByCSS(true);
			CTabFolder folderToTest = createTestCTabFolder("CTabFolder { swt-mru-visible: true}");
			assertEquals(true, folderToTest.getMRUVisible());
			assertEquals("true", engine.retrieveCSSProperty(folderToTest, "swt-mru-visible", null));
			folderToTest.getShell().close();
			folderToTest = createTestCTabFolder("CTabFolder { swt-mru-visible: false}");
			assertEquals("false", engine.retrieveCSSProperty(folderToTest, "swt-mru-visible", null));
			assertEquals(false, folderToTest.getMRUVisible());
		} finally {
			CSSPropertyMruVisibleSWTHandler.setMRUControlledByCSS(mruControlledByCSSDefault);
		}
	}

	@Test
	public void testMRUVisibleCSSControlOff() {
		final boolean mruControlledByCSSDefault = CSSPropertyMruVisibleSWTHandler.isMRUControlledByCSS();
		try {
			CSSPropertyMruVisibleSWTHandler.setMRUControlledByCSS(false);
			CTabFolder folderToTest = createTestCTabFolder("CTabFolder { swt-mru-visible: true}");
			assertEquals(false, folderToTest.getMRUVisible());
			assertEquals("false", engine.retrieveCSSProperty(folderToTest, "swt-mru-visible", null));
			folderToTest.getShell().close();
			folderToTest = createTestCTabFolder("CTabFolder { swt-mru-visible: false}");
			assertEquals("false", engine.retrieveCSSProperty(folderToTest, "swt-mru-visible", null));
			assertEquals(false, folderToTest.getMRUVisible());
		} finally {
			CSSPropertyMruVisibleSWTHandler.setMRUControlledByCSS(mruControlledByCSSDefault);
		}
	}

