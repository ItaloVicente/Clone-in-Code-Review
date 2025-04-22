	public void testOpenMatchingEditor() throws Exception {
		IProject proj = FileUtil.createProject("IEditorMatchingStrategyTest");
		IFile file1 = FileUtil.createFile("plugin.xml", proj);
		IFile file2 = FileUtil.createFile("MANIFEST.MF", proj);
		IFile file3 = FileUtil.createFile("build.properties", proj);
		IFile file4 = FileUtil.createFile("something.txt", proj);
