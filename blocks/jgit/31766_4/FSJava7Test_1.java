	@Test
	public void testExecutableAttributes() throws Exception {
		FS fs = FS.DETECTED;
		org.junit.Assume.assumeTrue(fs.getClass().equals(FS_POSIX_Java7.class));

		File f = new File(trash
		assertTrue(f.createNewFile());
		assertFalse(fs.canExecute(f));

		String umask = readUmask();
		if (umask == null)
			return;
		char others = umask.charAt(umask.length() - 1);

		boolean badUmask;
		if (others != '0' && others != '2' && others != '4' && others != '6') {
			badUmask = true;
		} else {
			badUmask = false;
		}

		Set<PosixFilePermission> permissions = readPermissions(f);
		assertTrue(!permissions.contains(PosixFilePermission.OTHERS_EXECUTE));
		assertTrue(!permissions.contains(PosixFilePermission.GROUP_EXECUTE));
		assertTrue(!permissions.contains(PosixFilePermission.OWNER_EXECUTE));

		fs.setExecute(f
		RepositoryTestCase.fsTick(f);
		permissions = readPermissions(f);
		assertTrue("'owner' execute permission not set"
				permissions.contains(PosixFilePermission.OWNER_EXECUTE));
		assertTrue("'group' execute permission not set"
				permissions.contains(PosixFilePermission.GROUP_EXECUTE));
		if (badUmask) {
			assertFalse("'others' execute permission set"
					permissions.contains(PosixFilePermission.OTHERS_EXECUTE));
			fail("Unexpected umask during test execution: " + umask);
		} else {
			assertTrue("'others' execute permission not set"
					permissions.contains(PosixFilePermission.OTHERS_EXECUTE));
		}
	}

	private String readUmask() throws Exception {
		Process p = Runtime.getRuntime().exec(
				new String[] { "sh"
		final BufferedReader lineRead = new BufferedReader(
				new InputStreamReader(p.getInputStream()
						.defaultCharset().name()));
		p.waitFor();
		return lineRead.readLine();
	}

	private Set<PosixFilePermission> readPermissions(File f) throws IOException {
		return Files
				.getFileAttributeView(f.toPath()
				.readAttributes().permissions();
	}

