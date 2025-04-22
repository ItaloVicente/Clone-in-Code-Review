	@Test
	public void testIndexDiffTwoSubmodules() throws Exception {
		try (Repository submodule2 = createWorkRepository()) {
			JGitTestUtil.writeTrashFile(submodule2
					"submodule2");
			Git subGit = Git.wrap(submodule2);
			subGit.add().addFilepattern("fileInSubmodule2").call();
			subGit.commit().setMessage("add file to submodule2").call();

			try (Repository sub2 = Git.wrap(db)
					.submoduleAdd().setPath("modules/submodule2")
					.setURI(submodule2.getDirectory().toURI().toString())
					.call()) {
				writeTrashFile("fileInRoot"
				Git rootGit = Git.wrap(db);
				rootGit.add().addFilepattern("fileInRoot").call();
				rootGit.commit().setMessage("add submodule2 and root file")
						.call();
				JGitTestUtil.writeTrashFile(submodule_db
						"submodule changed");
				JGitTestUtil.writeTrashFile(sub2
						"submodule2 changed");
				FileBasedConfig gitmodules = new FileBasedConfig(
						new File(db.getWorkTree()
						db.getFS());
				gitmodules.load();
				gitmodules.setString(ConfigConstants.CONFIG_SUBMODULE_SECTION
						"modules/submodule"
						"all");
				gitmodules.setString(ConfigConstants.CONFIG_SUBMODULE_SECTION
						"modules/submodule2"
						"none");
				gitmodules.save();
				IndexDiff indexDiff = new IndexDiff(db
						new FileTreeIterator(db));
				assertTrue(indexDiff.diff());
				String[] modified = indexDiff.getModified()
						.toArray(new String[0]);
				Arrays.sort(modified);
				assertEquals("[.gitmodules
						Arrays.toString(modified));
				gitmodules.setString(ConfigConstants.CONFIG_SUBMODULE_SECTION
						"modules/submodule"
						"dirty");
				gitmodules.save();
				indexDiff = new IndexDiff(db
						new FileTreeIterator(db));
				assertTrue(indexDiff.diff());
				modified = indexDiff.getModified().toArray(new String[0]);
				Arrays.sort(modified);
				assertEquals("[.gitmodules
						Arrays.toString(modified));
				StoredConfig cfg = db.getConfig();
				cfg.setString(ConfigConstants.CONFIG_SUBMODULE_SECTION
						"modules/submodule"
						"none");
				cfg.setString(ConfigConstants.CONFIG_SUBMODULE_SECTION
						"modules/submodule2"
						"all");
				cfg.save();
				indexDiff = new IndexDiff(db
						new FileTreeIterator(db));
				assertTrue(indexDiff.diff());
				modified = indexDiff.getModified().toArray(new String[0]);
				Arrays.sort(modified);
				assertEquals("[.gitmodules
						Arrays.toString(modified));
			}
		}
	}
