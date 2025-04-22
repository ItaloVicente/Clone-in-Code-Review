	@Ignore("test was commented before bug 443094")
	@Test
	public void testClassSelectedShowClose() {
		CTabFolder folder = createTestTabFolder();
		WidgetElement.setCSSClass(folder, "editorStack");

		CSSEngine engine = createEngine("CTabFolder.editorStack CTabItem { show-close: true }", folder.getDisplay());
		engine.applyStyles(folder.getShell(), true);

		for (int i = 0; i < folder.getItemCount(); i++) {
			assertTrue(folder.getItem(i).getShowClose());
		}
	}

	@Ignore("test was commented before bug 443094")
	@Test
	public void testFontsEditorStackClass() {
		CTabFolder folder = createTestTabFolder(false);
		CTabFolder folder2 = createFolder(folder.getShell());

		WidgetElement.setCSSClass(folder2, "editorStack");
		engine = createEngine("CTabItem { font-size: 10 }" + "CTabItem:selected { font-size: 14; font-weight: bold }"
				+ "CTabFolder.editorStack CTabItem { font-size: 11; }"
				+ "CTabFolder.editorStack CTabItem:selected { font-size: 13; font-style: italic }", folder.getDisplay());
		engine.applyStyles(folder.getShell(), true);

		folder.getShell().open();
		folder.setSelection(0);

		spinEventLoop();

		assertNotNull(folder.getSelection());
		assertNull(folder2.getSelection());

		for (int i = 0; i < folder.getItemCount(); i++) {
			CTabItem item = folder.getItem(i);
			FontData data = item.getFont().getFontData()[0];

			if (item == folder.getSelection()) {
				assertEquals(14, data.getHeight());
				assertEquals(SWT.BOLD, data.getStyle());
			} else {
				assertEquals(10, data.getHeight());
				assertEquals(SWT.NORMAL, data.getStyle());
			}
		}

		for (int i = 0; i < folder2.getItemCount(); i++) {
			CTabItem item = folder2.getItem(i);
			FontData data = item.getFont().getFontData()[0];

			assertEquals(11, data.getHeight());
			assertEquals(SWT.NORMAL, data.getStyle());
		}

		folder2.setSelection(0);
		spinEventLoop();

		for (int i = 0; i < folder2.getItemCount(); i++) {
			CTabItem item = folder2.getItem(i);
			FontData data = item.getFont().getFontData()[0];
			if (item == folder2.getSelection()) {
				assertEquals(13, data.getHeight());
				assertEquals(SWT.ITALIC | SWT.BOLD, data.getStyle());
			} else {
				assertEquals(11, data.getHeight());
				assertEquals(SWT.NORMAL, data.getStyle());
			}
		}
	}

	@Ignore("test was commented before bug 443094")
	@Test
	public void testFontsEditorStackClass2() {
		CTabFolder folder = createTestTabFolder(false);
		CTabFolder folder2 = createFolder(folder.getShell());

		WidgetElement.setCSSClass(folder2, "editorStack");
		engine = createEngine(
				"CTabItem { font-size: 10 }"
						+ "CTabItem:selected { font-size: 14; font-weight: bold }"
						+ "CTabFolder.editorStack CTabItem { font-size: 11; }"
						+ "CTabFolder.editorStack CTabItem:selected { font-size: 13; font-weight: normal; font-style: italic }",
						folder.getDisplay());
		engine.applyStyles(folder.getShell(), true);

		folder.getShell().open();
		folder.setSelection(0);

		spinEventLoop();

		assertNotNull(folder.getSelection());
		assertNull(folder2.getSelection());

		for (int i = 0; i < folder.getItemCount(); i++) {
			CTabItem item = folder.getItem(i);
			FontData data = item.getFont().getFontData()[0];

			if (item == folder.getSelection()) {
				assertEquals(14, data.getHeight());
				assertEquals(SWT.BOLD, data.getStyle());
			} else {
				assertEquals(10, data.getHeight());
				assertEquals(SWT.NORMAL, data.getStyle());
			}
		}

		for (int i = 0; i < folder2.getItemCount(); i++) {
			CTabItem item = folder2.getItem(i);
			FontData data = item.getFont().getFontData()[0];

			assertEquals(11, data.getHeight());
			assertEquals(SWT.NORMAL, data.getStyle());
		}

		folder2.setSelection(0);
		spinEventLoop();

		for (int i = 0; i < folder2.getItemCount(); i++) {
			CTabItem item = folder2.getItem(i);
			FontData data = item.getFont().getFontData()[0];
			if (item == folder2.getSelection()) {
				assertEquals(13, data.getHeight());
				assertEquals(SWT.ITALIC, data.getStyle());
			} else {
				assertEquals(11, data.getHeight());
				assertEquals(SWT.NORMAL, data.getStyle());
			}
		}
	}

	@Ignore("test was commented before bug 443094")
	@Test
	public void testShowCloseEditorStack() {
		CTabFolder folder = createTestTabFolder(false);
		CTabFolder folder2 = createFolder(folder.getShell());

		WidgetElement.setCSSClass(folder2, "editorStack");
		engine = createEngine("CTabItem { show-close: false }" + "CTabItem:selected { show-close: true }"
				+ "CTabFolder.editorStack CTabItem { show-close: true }", folder.getDisplay());
		engine.applyStyles(folder.getShell(), true);

		folder.getShell().open();
		folder.setSelection(0);

		spinEventLoop();

		assertNotNull(folder.getSelection());
		assertNull(folder2.getSelection());

		for (int i = 0; i < folder.getItemCount(); i++) {
			CTabItem item = folder.getItem(i);
			if (item == folder.getSelection()) {
				assertTrue(item.getShowClose());
			} else {
				assertFalse(item.getShowClose());
			}
		}

		for (int i = 0; i < folder2.getItemCount(); i++) {
			CTabItem item = folder2.getItem(i);
			assertTrue(item.getShowClose());
		}

		folder2.setSelection(0);
		spinEventLoop();

		for (int i = 0; i < folder2.getItemCount(); i++) {
			CTabItem item = folder2.getItem(i);
			assertTrue(item.getShowClose());
		}
	}

	@Ignore("test was commented before bug 443094")
	@Test
	public void testShowCloseViewStack() {
		CTabFolder folder = createTestTabFolder(false);
		CTabFolder folder2 = createFolder(folder.getShell());

		WidgetElement.setCSSClass(folder2, "viewStack");
		engine = createEngine("CTabItem { show-close: false }" + "CTabItem:selected { show-close: true }"
				+ "CTabFolder.viewStack CTabItem { show-close: false }"
				+ "CTabFolder.viewStack CTabItem.selected { show-close: true }", folder.getDisplay());
		engine.applyStyles(folder.getShell(), true);

		folder.getShell().open();
		folder.setSelection(0);

		spinEventLoop();

		assertNotNull(folder.getSelection());
		assertNull(folder2.getSelection());

		for (int i = 0; i < folder.getItemCount(); i++) {
			CTabItem item = folder.getItem(i);
			if (item == folder.getSelection()) {
				assertTrue(item.getShowClose());
			} else {
				assertFalse(item.getShowClose());
			}
		}

		for (int i = 0; i < folder2.getItemCount(); i++) {
			CTabItem item = folder2.getItem(i);
			assertFalse(item.getShowClose());
		}

		folder2.setSelection(0);
		spinEventLoop();

		for (int i = 0; i < folder2.getItemCount(); i++) {
			CTabItem item = folder.getItem(i);
			if (item == folder.getSelection()) {
				assertTrue(item.getShowClose());
			} else {
				assertFalse(item.getShowClose());
			}
		}
	}

	@Test
	public void testBackground() {
