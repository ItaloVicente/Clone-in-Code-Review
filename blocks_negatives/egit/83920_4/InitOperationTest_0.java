		assertEquals(FEATURE_PREFIX, getPrefix(repository, FEATURE_KEY));
		assertEquals(RELEASE_PREFIX, getPrefix(repository, RELEASE_KEY));
		assertEquals(HOTFIX_PREFIX, getPrefix(repository, HOTFIX_KEY));
		assertEquals(VERSION_TAG, getPrefix(repository, VERSION_TAG_KEY));
		assertEquals(DEVELOP, getBranch(repository, DEVELOP_KEY));
		assertEquals(MASTER, getBranch(repository, MASTER_KEY));
