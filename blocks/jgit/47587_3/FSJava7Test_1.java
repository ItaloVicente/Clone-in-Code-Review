		permissions = readPermissions(f);
		assertTrue("'owner' execute permission not set"
				permissions.contains(PosixFilePermission.OWNER_EXECUTE));
		assertFalse("'group' execute permission set"
				permissions.contains(PosixFilePermission.GROUP_EXECUTE));
		assertFalse("'others' execute permission set"
				permissions.contains(PosixFilePermission.OTHERS_EXECUTE));
