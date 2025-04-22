		assertTrue(permissions.contains(PosixFilePermission.OWNER_EXECUTE)
				"'owner' execute permission not set");
		assertTrue(permissions.contains(PosixFilePermission.GROUP_EXECUTE)
				"'group' execute permission not set");
		assertTrue(permissions.contains(PosixFilePermission.OTHERS_EXECUTE)
				"'others' execute permission not set");
