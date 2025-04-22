	
	@Test
	public void testIncludeUntracked() throws Exception {
		boolean include = Activator.getDefault().getPreferenceStore()
				.getBoolean(UIPreferences.COMMIT_DIALOG_INCLUDE_UNTRACKED);
		try {
			Activator
					.getDefault()
					.getPreferenceStore()
					.setValue(UIPreferences.COMMIT_DIALOG_INCLUDE_UNTRACKED,
							true);
			IProject prj = ResourcesPlugin.getWorkspace().getRoot().getProject(
					PROJ1);
			if (!prj.isAccessible())
				throw new IllegalStateException("No project found");
			IFile file = prj.getFile("untracked.txt");
			assertFalse(file.exists());
			file.create(
					new ByteArrayInputStream("new file".getBytes(prj
							.getDefaultCharset())), 0, null);
			assertTrue(file.exists());
			CommitDialogTester commitDialogTester = CommitDialogTester
					.openCommitDialog(PROJ1);
			assertEquals(1, commitDialogTester.getRowCount());
			assertTrue(commitDialogTester.isEntryChecked(0));
			String path = RepositoryMapping.getMapping(file)
					.getRepoRelativePath(file);
			assertEquals(path, commitDialogTester.getEntryText(0));
		} finally {
			Activator
					.getDefault()
					.getPreferenceStore()
					.setValue(UIPreferences.COMMIT_DIALOG_INCLUDE_UNTRACKED,
							include);
		}
	}
