	public void testRemoveExtension() {
		FileEditorMapping mapping1 = new FileEditorMapping(null, "testRemoveExtension1");
		FileEditorMapping mapping2 = new FileEditorMapping(null, "testRemoveExtension2");
		EditorDescriptor editor = EditorDescriptor.createForProgram("notepad.exe");
		mapping1.addEditor(editor);
		mapping2.addEditor(editor);
		FileEditorMapping[] src = (FileEditorMapping[]) fReg.getFileEditorMappings();
		FileEditorMapping[] maps = new FileEditorMapping[src.length + 2];
		System.arraycopy(src, 0, maps, 0, src.length);
		maps[maps.length - 1] = mapping1;
		maps[maps.length - 2] = mapping2;
		try {
			((EditorRegistry) fReg).setFileEditorMappings(maps);
			((EditorRegistry) fReg).saveAssociations();

			IEditorDescriptor editor1 = fReg.getDefaultEditor("a.testRemoveExtension1");
			assertEquals(editor, editor1);
			IEditorDescriptor editor2 = fReg.getDefaultEditor("a.testRemoveExtension2");
			assertEquals(editor, editor2);

			EditorDescriptor[] descriptors = new EditorDescriptor[] { editor };
			((EditorRegistry) fReg).removeExtension(null, descriptors);

			editor1 = fReg.getDefaultEditor("a.testRemoveExtension1");
			assertNull(editor1);
			editor2 = fReg.getDefaultEditor("a.testRemoveExtension2");
			assertNull(editor2);
			IFileEditorMapping[] mappings = fReg.getFileEditorMappings();
			assertEquals(src.length, mappings.length);
		} finally {
			((EditorRegistry) fReg).setFileEditorMappings(src);
			((EditorRegistry) fReg).saveAssociations();
		}
	}
