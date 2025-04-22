		try (Git git = new Git(repo)) {
			ObjectId masterId = repo.resolve("refs/heads/master");
			Ref newBranch = git.checkout().setCreateBranch(true)
					.setStartPoint(commitOfTag.name()).setName("toMerge")
					.call();
			ByteArrayInputStream bis = new ByteArrayInputStream(
					"Modified".getBytes("UTF-8"));
			ResourcesPlugin.getWorkspace().getRoot().getProject(PROJ1)
					.getFolder(FOLDER).getFile(FILE2)
					.setContents(bis, false, false, null);
			bis.close();
			git.commit().setAll(true).setMessage("To be merged").call();
			git.merge().include(masterId).call();
			String menuLabel = util
					.getPluginLocalizedValue("CompareWithPreviousAction.label");
			SWTBotShell selectDialog = openCompareWithDialog(menuLabel,
					UIText.CommitSelectDialog_WindowTitle);
			assertEquals(2, selectDialog.bot().table().rowCount());
			selectDialog.close();
			git.checkout().setName("refs/heads/master").call();
			git.branchDelete().setBranchNames(newBranch.getName())
					.setForce(true).call();
		}
