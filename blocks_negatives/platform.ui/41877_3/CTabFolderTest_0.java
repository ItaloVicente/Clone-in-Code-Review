		CTabFolder folderToTest = createTestCTabFolder("CTabFolder { mru-visible: true}");
		assertEquals(true, folderToTest.getMRUVisible());
		assertEquals("true", engine.retrieveCSSProperty(folderToTest, "mru-visible", null));
		folderToTest.getShell().close();
		folderToTest = createTestCTabFolder("CTabFolder { mru-visible: false}");
		assertEquals("false", engine.retrieveCSSProperty(folderToTest, "mru-visible", null));
		assertEquals(false, folderToTest.getMRUVisible());
