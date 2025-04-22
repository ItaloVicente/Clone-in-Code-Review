		if (badUmask) {
			assertFalse("'others' execute permission set",
					permissions.contains(PosixFilePermission.OTHERS_EXECUTE));
			System.err.println("WARNING: your system's umask: \"" + umask
					+ "\" doesn't allow FSJava7Test to test if setting posix"
					+ "  permissions for \"others\" works properly");
			assumeFalse(badUmask);
		} else {
			assertTrue("'others' execute permission not set",
					permissions.contains(PosixFilePermission.OTHERS_EXECUTE));
		}
	}
