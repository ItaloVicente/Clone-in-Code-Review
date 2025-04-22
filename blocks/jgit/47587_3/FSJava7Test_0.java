		assertTrue("'others' execute permission not set"
				permissions.contains(PosixFilePermission.OTHERS_EXECUTE));

		((FS_POSIX) fs).setUmask(0033);
		fs.setExecute(f
		assertFalse(fs.canExecute(f));
		fs.setExecute(f
