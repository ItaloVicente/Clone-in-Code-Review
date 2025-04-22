	@Test
	public void testFolderPathsResetWithRef() throws Exception {
		setupRepository();

		File folder = new File(db.getWorkTree()
		FileUtils.mkdirs(folder);

		File folderFile = new File(folder
		FileUtils.createNewFile(folderFile);
		PrintWriter writer = new PrintWriter(folderFile);
		writer.print("i'm in folder");
		writer.flush();
		writer.close();

		git.add().addFilepattern(folder.getName() + "/" + folderFile.getName())
				.call();

		git.commit().setMessage("adding folder").call();

		writer = new PrintWriter(folderFile);
		writer.print("i'm modified in folder");
		writer.flush();
		writer.close();

		git.add().addFilepattern(folder.getName() + "/" + folderFile.getName())
				.call();

		try {
			git.reset().setRef(Constants.HEAD)
					.addPath(folder.getName() + "/" + folderFile.getName())
					.call();
		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

