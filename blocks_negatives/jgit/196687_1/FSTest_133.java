		assertTrue("'owner' execute permission not set",
				permissions.contains(PosixFilePermission.OWNER_EXECUTE));
		assertTrue("'group' execute permission not set",
				permissions.contains(PosixFilePermission.GROUP_EXECUTE));
		assertTrue("'others' execute permission not set",
				permissions.contains(PosixFilePermission.OTHERS_EXECUTE));
