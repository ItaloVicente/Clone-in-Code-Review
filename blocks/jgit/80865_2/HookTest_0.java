	@Test
	public void testFindPostCommitHook() throws Exception {
		assumeSupportedPlatform();

		assertNull("no hook should be installed"
				FS.DETECTED.findHook(db
		File hookFile = writeHookFile(PostCommitHook.NAME
				"#!/bin/bash\necho \"test $1 $2\"");
		assertEquals("expected to find pre-commit hook"
				FS.DETECTED.findHook(db
	}

