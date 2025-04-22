
	public void testGetShellAfterClose() throws Throwable {
		IProject proj = FileUtil.createProject("IEditorPartTest");
		IFile file = FileUtil.createFile("IEditorPartTest.txt", proj);
		MockEditorWithState editor = (MockEditorWithState) fPage.openEditor(new FileEditorInput(file),
				MockEditorWithState.ID);

		assertNotNull(editor.getSite().getShell());

		closePart(fPage, editor);
		processEvents();

		Map<String, List<IStatus>> errors = new LinkedHashMap<>();
		ILogListener listener = (status, plugin) -> {
			List<IStatus> list = errors.get(plugin);
			if (list == null) {
				list = new ArrayList<>();
				errors.put(status.getPlugin(), list);
			}
			list.add(status);
		};

		try {
			Platform.addLogListener(listener);

			closePart(fPage, editor);
			processEvents();

			editor.getSite().getShell();
		} finally {
			Platform.removeLogListener(listener);
		}

		List<IStatus> list = errors.get("org.eclipse.ui.workbench");
		if (list == null || list.isEmpty()) {
			fail("No error reported on accessing shell after part disposal");
		}
		assertEquals(1, list.size());
		Throwable ex = list.get(0).getException();
		assertTrue("Unexpected exception: " + ex, ex instanceof IllegalStateException);
		assertTrue("Unexpected exception message: " + ex.getMessage(),
				ex.getMessage().contains("IWorkbenchSite.getShell() was called after part disposal"));
	}

