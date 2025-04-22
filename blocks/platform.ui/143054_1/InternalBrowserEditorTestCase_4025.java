	@Test
	public void testBrowserID() throws Exception {
		shell = WebBrowserTestsPlugin.getInstance().getWorkbench().getActiveWorkbenchWindow().getShell();
		WebBrowserPreference.setBrowserChoice(WebBrowserPreference.INTERNAL);
		IWorkbenchBrowserSupport wbs = WebBrowserTestsPlugin.getInstance().getWorkbench().getBrowserSupport();
		IWebBrowser wb = wbs.createBrowser(IWorkbenchBrowserSupport.AS_EDITOR, "test", "MyBrowser", "A tooltip");

		wb.openURL(new URL("http://www.ibm.com"));
		runLoopTimer(2);

		IEditorReference[] editorRefs = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.getEditorReferences();
		IEditorReference editor = editorRefs[0];
		IEditorPart editorPart = editor.getEditor(true);

		WebBrowserEditorInput editorInput = (WebBrowserEditorInput) editor.getEditorInput();
		assertNotNull(editorInput.getBrowserId());
		assertEquals(wb.getId(), editorInput.getBrowserId());

		wb.close();
		runLoopTimer(2);
	}

