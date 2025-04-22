	@Test
	public void testCheckoutWithConflictsAndReset() throws Exception {
		String charset = ResourcesPlugin.getWorkspace().getRoot()
				.getProject(PROJ1).getDefaultCharset();
		String file1Content = getTestFileContent();
		checkoutAndVerify(new String[] { LOCAL_BRANCHES, "stable" });
		ResourcesPlugin.getWorkspace().getRoot().getProject(PROJ1)
				.getFolder(FOLDER).getFile(FILE1).setContents(
						new ByteArrayInputStream(
								"New content".getBytes(charset)),
						IResource.NONE, null);
		String file2Content = "Also changed";
		ResourcesPlugin.getWorkspace().getRoot().getProject(PROJ1)
				.getFolder(FOLDER).getFile(FILE2).setContents(
						new ByteArrayInputStream(
								file2Content.getBytes(charset)),
						IResource.NONE, null);
		checkout(new String[] { LOCAL_BRANCHES, "master" });
		SWTBotShell showConflicts = bot
				.shell(UIText.BranchResultDialog_CheckoutConflictsTitle);
		SWTBot shellBot = showConflicts.bot();
		SWTBotTreeItem[] items = shellBot.tree().getAllItems();
		assertEquals(1, items.length);
		assertEquals(FILE1, items[0].getItems()[0].getItems()[0].getText());
		shellBot.button(UIText.BranchResultDialog_buttonDiscardChanges).click();
		TestUtils.waitForJobs(500, 5000, JobFamilies.DISCARD_CHANGES);
		TestUtil.joinJobs(JobFamilies.CHECKOUT);
		assertEquals(file1Content, getTestFileContent());
		assertEquals(file2Content, getTestFileContent(FILE2));
	}

