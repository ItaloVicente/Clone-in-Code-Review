	@AfterClass
	public static void shutdown() {
		perspective.activate();
	}

	@After
	public void rollback() throws Exception {
		ResetOperation rop = new ResetOperation(repo, TAG_NAME, ResetType.HARD);
		rop.execute(null);
		IProject project = ResourcesPlugin.getWorkspace().getRoot()
				.getProject(PROJ1);
		project.getFile(PATCH_FILE).delete(true, null);
	}

	@Test
	public void testNoChanges() throws Exception {
		CreatePatchWizard.openWizard(PROJ1);
		NoChangesPopup popup = new NoChangesPopup(
				bot.shell(UIText.GitCreatePatchAction_cannotCreatePatch));
		popup.cancelPopup();
	}

