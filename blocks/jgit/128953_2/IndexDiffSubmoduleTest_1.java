		assertEquals("[]"
		assertEquals("[modules/submodule]"
	}

	@Theory
	public void testFileReplacedBySubmodule(IgnoreSubmoduleMode mode)
			throws Exception {
		Repository db2 = cloneWithoutCloningSubmodule();
		File submodule = new File(new File(db2.getWorkTree()
				"submodule");
		recursiveDelete(submodule);
		Files.copy(
				new ByteArrayInputStream(
						"nonsense".getBytes(StandardCharsets.UTF_8))
				submodule.toPath());
		try (Git git = new Git(db2)) {
			git.add().addFilepattern("modules/submodule").call();
			git.commit().setMessage("File").call();
			assertTrue(submodule.delete());
			FileUtils.mkdirs(submodule);
			git.submoduleInit().addPath("modules/submodule").call();
			IndexDiff indexDiff = new IndexDiff(db2
					new FileTreeIterator(db2));
			indexDiff.setIgnoreSubmoduleMode(mode);
			assertTrue(indexDiff.diff());
			assertEquals("[modules/submodule]"
					indexDiff.getMissing().toString());
			assertEquals("[]"
			assertEquals("[]"
		}
	}

	@Theory
	public void testFileReplacedBySubmoduleStaged(IgnoreSubmoduleMode mode)
			throws Exception {
		Repository db2 = cloneWithoutCloningSubmodule();
		File submodule = new File(new File(db2.getWorkTree()
				"submodule");
		recursiveDelete(submodule);
		Files.copy(
				new ByteArrayInputStream(
						"nonsense".getBytes(StandardCharsets.UTF_8))
				submodule.toPath());
		try (Git git = new Git(db2)) {
			git.add().addFilepattern("modules/submodule").call();
			git.commit().setMessage("File").call();
			assertTrue(submodule.delete());
			FileUtils.mkdirs(submodule);
			git.submoduleInit().addPath("modules/submodule").call();
			git.add().addFilepattern("modules/submodule").call();
			IndexDiff indexDiff = new IndexDiff(db2
					new FileTreeIterator(db2));
			indexDiff.setIgnoreSubmoduleMode(mode);
			assertTrue(indexDiff.diff());
			assertEquals("[modules/submodule]"
					indexDiff.getRemoved().toString());
			assertEquals("[]"
			assertEquals("[]"
			assertEquals("[]"
		}
	}

	@Theory
	public void testSubmoduleDeinited(IgnoreSubmoduleMode mode)
			throws Exception {
		try (Git git = new Git(db)) {
			git.submoduleDeinit().addPath("modules/submodule").call();
			IndexDiff indexDiff = new IndexDiff(db
					new FileTreeIterator(db));
			indexDiff.setIgnoreSubmoduleMode(mode);
			assertFalse(indexDiff.diff());
		}
	}

	@Ignore("Bug 538758: RmCommand cannot remove submodules")
	@Theory
	public void testSubmoduleRemoved(IgnoreSubmoduleMode mode)
			throws Exception {
		try (Git git = new Git(db)) {
			git.rm().addFilepattern("modules/submodule").call();
			IndexDiff indexDiff = new IndexDiff(db
					new FileTreeIterator(db));
			indexDiff.setIgnoreSubmoduleMode(mode);
			assertTrue(indexDiff.diff());
			assertEquals("[]"
			assertEquals("[]"
			assertEquals("[.gitmodules]"
			if (mode != IgnoreSubmoduleMode.ALL) {
				assertEquals("[modules/submodule]"
						indexDiff.getRemoved().toString());
			} else {
				assertEquals("[]"
			}
			assertFalse(submodule_trash.exists());
			assertTrue(submodule_trash.getParentFile().exists());
		}
