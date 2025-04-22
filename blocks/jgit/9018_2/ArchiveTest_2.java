	@Test
	public void testTarPreservesMode() throws Exception {
		writeTrashFile("plain"
		writeTrashFile("executable"
		writeTrashFile("symlink"
		git.add().addFilepattern("plain").call();
		git.add().addFilepattern("executable").call();
		git.add().addFilepattern("symlink").call();

		DirCache cache = db.lockDirCache();
		cache.getEntry("executable").setFileMode(FileMode.EXECUTABLE_FILE);
		cache.getEntry("symlink").setFileMode(FileMode.SYMLINK);
		cache.write();
		cache.commit();
		cache.unlock();

		git.commit().setMessage("three files with different modes").call();

				"git archive --format=tar master"
		writeRaw("with-modes.tar"
		assertTarContainsEntry("with-modes.tar"
		assertTarContainsEntry("with-modes.tar"
		assertTarContainsEntry("with-modes.tar"
	}

