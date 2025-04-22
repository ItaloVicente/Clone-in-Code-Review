	@Test
	public void testCompareWithPrevious() throws Exception {
		String menuLabel = util
				.getPluginLocalizedValue("CompareWithPreviousAction.label");
		clickCompareWith(menuLabel);
		waitUntilCompareTreeViewTreeHasNodeCount(1);
	}

	@Test
	public void testCompareWithPreviousWithMerge() throws Exception {
		Repository repo = lookupRepository(repositoryFile);

		Git git = new Git(repo);
		ObjectId masterId = repo.resolve("refs/heads/master");
		Ref newBranch = git.checkout().setCreateBranch(true)
				.setStartPoint(commitOfTag.name()).setName("toMerge").call();
		ByteArrayInputStream bis = new ByteArrayInputStream(
				"Modified".getBytes());
		ResourcesPlugin.getWorkspace().getRoot().getProject(PROJ1)
				.getFolder(FOLDER).getFile(FILE2)
				.setContents(bis, false, false, null);
		bis.close();
		git.commit().setAll(true).setMessage("To be merged").call();
		git.merge().include(masterId).call();
		String menuLabel = util
				.getPluginLocalizedValue("CompareWithPreviousAction.label");
		clickCompareWith(menuLabel);
		SWTBotShell selectDialog = bot.shell(UIText.CommitSelectDialog_WindowTitle);
		assertEquals(2, selectDialog.bot().table().rowCount());
		selectDialog.close();
		git.checkout().setName("refs/heads/master").call();
		git.branchDelete().setBranchNames(newBranch.getName()).setForce(true).call();
	}

