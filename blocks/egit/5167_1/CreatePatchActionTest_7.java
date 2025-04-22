		CreatePatchWizard createPatchWizard = openCreatePatchWizard();
		LocationPage locationPage = createPatchWizard.getLocationPage();
		locationPage.selectWorkspace("/" + PROJ1 + "/" + PATCH_FILE);
		createPatchWizard.finish();

		bot.waitUntil(Conditions.shellCloses(createPatchWizard.getShell()));

		IFile patch = ResourcesPlugin.getWorkspace().getRoot()
				.getProject(PROJ1).getFile(PATCH_FILE);
		byte[] bytes = IO.readFully(patch.getLocation().toFile());
		String patchContent = new String(bytes, patch.getCharset());

		assertEquals(EXPECTED_PATCH_CONTENT, patchContent);
