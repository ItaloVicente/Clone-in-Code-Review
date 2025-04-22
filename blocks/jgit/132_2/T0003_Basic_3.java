	public void test000_openRepoBadArgs() throws IOException {
		try {
			new Repository(null
			fail("Must pass either GIT_DIR or GIT_WORK_TREE");
		} catch (IllegalArgumentException e) {
			assertEquals(
					"Either GIT_DIR or GIT_WORK_TREE must be passed to Repository constructor"
					e.getMessage());
		}
	}

	public void test000_openrepo_default_gitDirSet() throws IOException {
		File repo1Parent = new File(trash.getParentFile()
		Repository repo1initial = new Repository(new File(repo1Parent
		repo1initial.create();
		repo1initial.close();

		File theDir = new File(repo1Parent
		Repository r = new Repository(theDir
		assertEqualsPath(theDir
		assertEqualsPath(repo1Parent
		assertEqualsPath(new File(theDir
		assertEqualsPath(new File(theDir
	}

	public void test000_openrepo_default_workDirSet() throws IOException {
		File repo1Parent = new File(trash.getParentFile()
		Repository repo1initial = new Repository(new File(repo1Parent
		repo1initial.create();
		repo1initial.close();

		File theDir = new File(repo1Parent
		Repository r = new Repository(null
		assertEqualsPath(theDir
		assertEqualsPath(repo1Parent
		assertEqualsPath(new File(theDir
		assertEqualsPath(new File(theDir
	}

	public void test000_openrepo_default_absolute_workdirconfig()
			throws IOException {
		File repo1Parent = new File(trash.getParentFile()
		File workdir = new File(trash.getParentFile()
		workdir.mkdir();
		Repository repo1initial = new Repository(new File(repo1Parent
		repo1initial.create();
		repo1initial.getConfig().setString("core"
				workdir.getAbsolutePath());
		repo1initial.getConfig().save();
		repo1initial.close();

		File theDir = new File(repo1Parent
		Repository r = new Repository(theDir
		assertEqualsPath(theDir
		assertEqualsPath(workdir
		assertEqualsPath(new File(theDir
		assertEqualsPath(new File(theDir
	}

	public void test000_openrepo_default_relative_workdirconfig()
			throws IOException {
		File repo1Parent = new File(trash.getParentFile()
		File workdir = new File(trash.getParentFile()
		workdir.mkdir();
		Repository repo1initial = new Repository(new File(repo1Parent
		repo1initial.create();
		repo1initial.getConfig()
				.setString("core"
		repo1initial.getConfig().save();
		repo1initial.close();

		File theDir = new File(repo1Parent
		Repository r = new Repository(theDir
		assertEqualsPath(theDir
		assertEqualsPath(workdir
		assertEqualsPath(new File(theDir
		assertEqualsPath(new File(theDir
	}

	public void test000_openrepo_alternate_index_file_and_objdirs()
			throws IOException {
		File repo1Parent = new File(trash.getParentFile()
		File indexFile = new File(trash
		File objDir = new File(trash
		File[] altObjDirs = new File[] { db.getObjectsDirectory() };
		Repository repo1initial = new Repository(new File(repo1Parent
		repo1initial.create();
		repo1initial.close();

		File theDir = new File(repo1Parent
		Repository r = new Repository(theDir
				indexFile);
		assertEqualsPath(theDir
		assertEqualsPath(theDir.getParentFile()
		assertEqualsPath(indexFile
		assertEqualsPath(objDir
		assertNotNull(r.mapCommit("6db9c2ebf75590eef973081736730a9ea169a0c4"));
	}

	protected void assertEqualsPath(File expected
			throws IOException {
		assertEquals(expected.getCanonicalPath()
	}

