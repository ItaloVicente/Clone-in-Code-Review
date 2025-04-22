	@Test
	public void testNoChangesInSelection() throws Exception {
		IFile fileToStage = touch(PROJ1, "folder/test.txt", "new content in "
				+ PROJ1);
		stage(fileToStage);
		touch(PROJ2, "folder/test.txt", "new content in " + PROJ2);

		CreatePatchWizard.openWizard(PROJ1);

		NoChangesPopup popup = new NoChangesPopup(
				bot.shell(UIText.GitCreatePatchAction_cannotCreatePatch));
		popup.cancelPopup();
	}

