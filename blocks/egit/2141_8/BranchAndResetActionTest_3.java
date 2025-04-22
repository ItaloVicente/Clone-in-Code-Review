	@Test
	public void testCheckoutWithConflicts() throws Exception {
		String charset = ResourcesPlugin.getWorkspace().getRoot().getProject(
				PROJ1).getDefaultCharset();
		try {
			checkoutAndVerify(new String[] { LOCAL_BRANCHES, "master" },
					new String[] { LOCAL_BRANCHES, "stable" });
			ResourcesPlugin.getWorkspace().getRoot().getProject(PROJ1)
					.getFolder(FOLDER).getFile(FILE1).setContents(
							new ByteArrayInputStream("New content"
									.getBytes(charset)), IResource.NONE, null);
			checkout(new String[] { LOCAL_BRANCHES, "master" });
			SWTBotShell showConflicts = bot
					.shell(UIText.BranchResultDialog_CheckoutConflictsTitle);
			assertEquals(FILE1, showConflicts.bot().tree().getAllItems()[0]
					.getItems()[0].getItems()[0].getText());
			showConflicts.close();
		} finally {
			ResourcesPlugin.getWorkspace().getRoot().getProject(PROJ1)
					.getFolder(FOLDER).getFile(FILE1).setContents(
							new ByteArrayInputStream("Hello, world"
									.getBytes(charset)), IResource.NONE, null);
		}
	}

	@Test
	public void testCheckoutWithNonDeleted() throws Exception {
		IFile test = ResourcesPlugin.getWorkspace().getRoot().getProject(PROJ1)
				.getFolder(FOLDER).getFile("temp.txt");
		test.create(new ByteArrayInputStream(new byte[0]), false, null);
		File testFile = new File(test.getLocation().toString());
		assertTrue(testFile.exists());
		FileInputStream fis = new FileInputStream(testFile);
		try {
			FileUtils.delete(testFile);
			return;
		} catch (IOException e) {
		} finally {
			fis.close();
			if (testFile.exists())
				FileUtils.delete(testFile);
		}
		final Image folderImage = PlatformUI.getWorkbench().getSharedImages()
				.getImage(ISharedImages.IMG_OBJ_FOLDER);

		final Image projectImage = PlatformUI.getWorkbench().getSharedImages()
				.getImage(SharedImages.IMG_OBJ_PROJECT);
		checkoutAndVerify(new String[] { LOCAL_BRANCHES, "master" },
				new String[] { LOCAL_BRANCHES, "stable" });
		IFile toBeDeleted = ResourcesPlugin.getWorkspace().getRoot()
				.getProject(PROJ1).getFolder(FOLDER).getFile("ToBeDeleted");
		toBeDeleted.create(new ByteArrayInputStream(new byte[0]), false, null);

		ArrayList<IFile> untracked = new ArrayList<IFile>();
		untracked.add(toBeDeleted);
		CommitOperation op = new CommitOperation(new IFile[] { toBeDeleted },
				new ArrayList<IFile>(), untracked, TestUtil.TESTAUTHOR,
				TestUtil.TESTCOMMITTER, "Add to stable");
		op.execute(null);

		InputStream is = toBeDeleted.getContents();
		try {
			checkout(new String[] { LOCAL_BRANCHES, "master" });
			final SWTBotShell showUndeleted = bot
					.shell(UIText.NonDeletedFilesDialog_NonDeletedFilesTitle);
			assertEquals("ToBeDeleted", showUndeleted.bot().tree()
					.getAllItems()[0].getItems()[0].getItems()[0].getText());
			Display.getDefault().syncExec(new Runnable() {
				public void run() {
					assertSame(folderImage, showUndeleted.bot().tree()
							.getAllItems()[0].widget.getImage());
				}
			});

			showUndeleted.bot().radio(
					UIText.NonDeletedFilesTree_FileSystemPathsButton).click();
			IPath path = new Path(lookupRepository(repositoryFile)
					.getWorkTree().getPath()).append(PROJ1).append(FOLDER)
					.append("ToBeDeleted");
			SWTBotTreeItem[] items = showUndeleted.bot().tree().getAllItems();
			for (int i = 0; i < path.segmentCount(); i++) {
				boolean found = false;
				String segment = path.segment(i);
				for (SWTBotTreeItem item : items) {
					if (item.getText().equals(segment)) {
						found = true;
						items = item.getItems();
					}
				}
				assertTrue(found);
			}

			showUndeleted.bot().radio(
					UIText.NonDeletedFilesTree_ResourcePathsButton).click();
			assertEquals("ToBeDeleted", showUndeleted.bot().tree()
					.getAllItems()[0].getItems()[0].getItems()[0].getText());
			Display.getDefault().syncExec(new Runnable() {
				public void run() {
					assertSame(projectImage, showUndeleted.bot().tree()
							.getAllItems()[0].widget.getImage());
				}
			});

			ICondition treeEmpty = new ICondition() {

				public boolean test() throws Exception {
					return showUndeleted.bot().tree().getAllItems().length == 0;
				}

				public void init(SWTBot actBot) {
				}

				public String getFailureMessage() {
					return "Not deleted";
				}
			};

			showUndeleted.bot().button(
					UIText.NonDeletedFilesDialog_RetryDeleteButton).click();
			try {
				showUndeleted.bot().waitUntil(treeEmpty, 1000, 100);
				fail("Should have failed");
			} catch (TimeoutException e) {
			}
			is.close();
			showUndeleted.bot().button(
					UIText.NonDeletedFilesDialog_RetryDeleteButton).click();
			showUndeleted.bot().waitUntil(treeEmpty, 1000, 100);
			showUndeleted.close();
		} finally {
			is.close();
		}
	}

