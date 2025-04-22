		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		String projectPath1 = workspace.getRoot().getProject(projectName1)
				.getLocation().toOSString();
		String projectPath2 = workspace.getRoot().getProject(projectName2)
				.getLocation().toOSString();
		existingOrNewPage.assertContents(
				new Row[] {
						new Row(true, projectName1, projectPath1, ".."
								+ File.separator + ".git"),
						new Row(false, projectName2, projectPath2, "", new Row[] {
								new Row(false, ".", "", ".git"),
								new Row(false, "..", "", ".." + File.separator
										+ ".git"), }) }, "");
		bot.tree().getAllItems()[1].getItems()[0].check();
		existingOrNewPage.assertEnabling(false, false, true);
		bot.button("Finish").click();
		Thread.sleep(1000);
		assertEquals(repo1.getDirectory().getCanonicalPath(), RepositoryMapping
				.getMapping(workspace.getRoot().getProject(projectName1))
				.getRepository().getDirectory().toString());
		assertEquals(repo2.getDirectory().getCanonicalPath(), RepositoryMapping
				.getMapping(workspace.getRoot().getProject(projectName2))
				.getRepository().getDirectory().toString());
	}
