	@Test
	public void testNewRepositoryCaseSensitive()
			throws CoreException, IOException {
		if (FS.detect().isCaseSensitive()) {
			return;
		}
		Repository repository = FileRepositoryBuilder.create(gitDir);
		repository.create();
		repository.close();

		IPath path = new Path(gitDir.toString());
		String device = path.getDevice();
		if (device == null) {
			return;
		}
		if (!device.toLowerCase().equals(device)) {
			path = path.setDevice(device.toLowerCase());
		} else {
			path = path.setDevice(device.toUpperCase());
		}
		assertNotEquals(path, new Path(gitDir.toString()));
		assertNotEquals(path.toFile().toString(),
				new Path(gitDir.toString()).toFile().toString());
		assertEquals(path.toFile(), gitDir);

		ConnectProviderOperation operation = new ConnectProviderOperation(
				project.getProject(),
				path.toFile());
		operation.execute(null);

		assertTrue(RepositoryProvider.isShared(project.getProject()));

		assertTrue(gitDir.exists());
	}

