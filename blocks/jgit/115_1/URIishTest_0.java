
	public void testGetNullHumanishName() {
		try {
			URIish.getHumanishName(null);
			fail("path must be not null");
		} catch (NullPointerException e) {
		}
	}

	public void testGetEmptyHumanishName() {
		try {
			URIish.getHumanishName("");
			fail("empty path is useless");
		} catch (IllegalArgumentException e) {
		}
	}

	public void testGetDotGitHumanishName() {
		try {
			URIish.getHumanishName(".git");
			fail("path '.git' is useless");
		} catch (IllegalArgumentException e) {
		}
	}

	public void testGetValidDotGitHumanishName() {
		String humanishName = URIish.getHumanishName("abc.git");
		assertEquals("abc"
	}

	public void testGetValidWithSlashDotGitHumanishName() {
		String humanishName = URIish.getHumanishName("/abc.git");
		assertEquals("abc"
	}
