		assertTrue(permissions.contains(PosixFilePermission.OWNER_EXECUTE)
				"'owner' execute permission not set");
		assertFalse(permissions.contains(PosixFilePermission.GROUP_EXECUTE)
				"'group' execute permission set");
		assertFalse(permissions.contains(PosixFilePermission.OTHERS_EXECUTE)
				"'others' execute permission set");
