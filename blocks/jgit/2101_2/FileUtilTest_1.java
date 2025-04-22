
	public void testMkdir() throws IOException {
		File d = new File(trash
		FileUtils.mkdir(d);
		assertTrue(d.exists() && d.isDirectory());

		try {
			FileUtils.mkdir(d);
			fail("creation of existing directory must fail");
		} catch (IOException e) {
		}

		assertTrue(d.delete());
		File f = new File(trash
		assertTrue(f.createNewFile());
		try {
			FileUtils.mkdir(d);
			fail("creation of directory having same path as existing file must"
					+ " fail");
		} catch (IOException e) {
		}
		assertTrue(f.delete());
	}

	public void testMkdirs() throws IOException {
		File root = new File(trash
		assertTrue(root.mkdir());

		File d = new File(root
		FileUtils.mkdirs(d);
		assertTrue(d.exists() && d.isDirectory());

		try {
			FileUtils.mkdirs(d);
			fail("creation of existing directory hierarchy must fail");
		} catch (IOException e) {
		}

		FileUtils.delete(root
		File f = new File(trash
		assertTrue(f.createNewFile());
		try {
			FileUtils.mkdirs(d);
			fail("creation of directory having path conflicting with existing"
					+ " file must fail");
		} catch (IOException e) {
		}
		assertTrue(f.delete());
	}

