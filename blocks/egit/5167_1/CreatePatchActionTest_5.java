
		CreatePatchWizard createPatchWizard = openCreatePatchWizard();
		createPatchWizard.finish();

		bot.waitUntil(Conditions.shellCloses(createPatchWizard.getShell()));

		assertClipboard(EXPECTED_PATCH_CONTENT);
	}

	@Test
	public void testFilesystem() throws Exception {
		touchAndSubmit("oldContent", null);
		touch("newContent");
		waitInUI();

