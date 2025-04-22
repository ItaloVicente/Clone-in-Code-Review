	@Test
	public void testPushTagPreselectsTag() throws Exception {
		createTag("tag-to-push", "Tag to push");
		SWTBotTree tree = getOrOpenView().bot().tree();
		myRepoViewUtil.getTagsItem(tree, repositoryFile)
				.expand().getNode("tag-to-push").select();
		ContextMenuHelper.clickContextMenu(tree,
				myUtil.getPluginLocalizedValue("RepoViewPushTag.label"));

		PushTagsWizardTester tester = PushTagsWizardTester.forShell();
		tester.assertTagChecked("tag-to-push");
		tester.cancel();
	}

