	@Ignore("this test was commented before bug 443094")
	@Test
	public void testBorderVisible() {
		CTabFolder folderToTest = createTestCTabFolder("CTabFolder { border-visible: true}");
		assertEquals(true, folderToTest.getBorderVisible());
		assertEquals("true", engine.retrieveCSSProperty(folderToTest, "border-visible", null));
		folderToTest.getShell().close();
		folderToTest = createTestCTabFolder("CTabFolder { border-visible: false}");
		assertEquals(false, folderToTest.getBorderVisible());
		assertEquals("false", engine.retrieveCSSProperty(folderToTest, "border-visible", null));
	}
	@Test
	public void testSimple() {
