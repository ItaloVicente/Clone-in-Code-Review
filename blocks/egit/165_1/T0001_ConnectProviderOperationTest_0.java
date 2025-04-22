	public void testNewRepositoryWithLink() throws CoreException, IOException {

		File tmpDir = createTempDirectory("egit", ".tmp");
		File srcDir = new File(tmpDir, "src");
		srcDir.mkdir();
		File gitDir = new File(tmpDir, ".git");
		Repository repository = new Repository(gitDir);
		repository.create();
		repository.close();

		try {
			project.createLinkedSourceFolder(srcDir);
			ConnectProviderOperation operation = new ConnectProviderOperation(
					project.getProject(), new File("src/../.git"));
			operation.run(null);

			assertTrue(RepositoryProvider.isShared(project.getProject()));

			assertTrue(gitDir.exists());
		} finally {
			delete(tmpDir);
		}
	}

	private File createTempDirectory(String prefix, String suffix) {
		File tmpDir = new File(System.getProperty("java.io.tmpdir"));
		File f;
		int counter = new Random().nextInt() & 0xffff;
		do {
			f = new File(tmpDir, prefix + counter + suffix);
			counter++;
		} while (!f.mkdir());
		return f;
	}

	private boolean delete(File dir) {
		if (!dir.isDirectory())
			return dir.delete();

		for(File f: dir.listFiles()) {
			if (!delete(new File(dir, f.getName())))
				return false;
		}

		return dir.delete();
	}

