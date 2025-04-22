	@Test
	public void testWorkspaceHeader() throws Exception {
		touchAndSubmit("oldContent", null);
		touch("newContent");
		URI fileLocationUri = ResourcesPlugin.getWorkspace().getRoot()
				.getProject(PROJ1).getFolder(FOLDER).getFile(FILE2)
				.getLocationURI();
		FileUtils.delete(new File(fileLocationUri));
		IProject secondProject = ResourcesPlugin.getWorkspace().getRoot()
				.getProject(PROJ2);
		IFile newFile = secondProject.getFile(FILE1);
		newFile.create(
				new ByteArrayInputStream("Hello, world".getBytes(secondProject
						.getDefaultCharset())), false, null);
		waitInUI();

		CreatePatchWizard createPatchWizard = openCreatePatchWizard();
		LocationPage locationPage = createPatchWizard.getLocationPage();
		locationPage.selectWorkspace("/" + PROJ1 + "/" + PATCH_FILE);
		OptionsPage optionsPage = locationPage.nextToOptionsPage();
		optionsPage.setFormat(CoreText.DiffHeaderFormat_Workspace);
		createPatchWizard.finish();

		bot.waitUntil(Conditions.shellCloses(createPatchWizard.getShell()));

		IFile patch = ResourcesPlugin.getWorkspace().getRoot()
				.getProject(PROJ1).getFile(PATCH_FILE);
		byte[] bytes = IO.readFully(patch.getLocation().toFile());
		String patchContent = new String(bytes, patch.getCharset());

		assertEquals(EXPECTED_WORKSPACE_PATCH_CONTENT, patchContent);
	}

