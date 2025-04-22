
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

	public void testGetValidWithEmptySlashDotGitHumanishName() {
		String humanishName = URIish.getHumanishName("/a/b/.git");
		assertEquals("b"
	}

	public void testGetWithSlashDotGitHumanishName() {
		try {
			URIish.getHumanishName("/.git");
			fail("never returns an empty value");
		} catch (IllegalArgumentException e) {
		}
	}

	public void testGetTwoSlashesDotGitHumanishName() {
		try {
			fail("never returns an empty value");
		} catch (IllegalArgumentException e) {
		}
	}

	public void testGetValidHumanishName() {
		String humanishName = URIish.getHumanishName("abc");
		assertEquals("abc"
	}

	public void testGetValidSlashHumanishName() {
		String humanishName = URIish.getHumanishName("abc/");
		assertEquals("abc"
	}

	public void testGetSlashValidSlashHumanishName() {
		String humanishName = URIish.getHumanishName("/abc/");
		assertEquals("abc"
	}

	public void testGetSlashValidSlashDotGitSlashHumanishName() {
		String humanishName = URIish.getHumanishName("/abc/.git");
		assertEquals("abc"
	}

	public void testGetSlashesValidSlashHumanishName() {
		String humanishName = URIish.getHumanishName("/a/b/c/");
		assertEquals("c"
	}

	public void testGetValidDotGitHumanishName() {
		String humanishName = URIish.getHumanishName("abc.git");
		assertEquals("abc"
	}

	public void testGetValidDotGitSlashHumanishName() {
		String humanishName = URIish.getHumanishName("abc.git/");
		assertEquals("abc"
	}

	public void testGetValidWithSlashDotGitHumanishName() {
		String humanishName = URIish.getHumanishName("/abc.git");
		assertEquals("abc"
	}

	public void testGetValidWithSlashDotGitSlashHumanishName() {
		String humanishName = URIish.getHumanishName("/abc.git/");
		assertEquals("abc"
	}

	public void testGetValidWithSlashesDotGitHumanishName() {
		String humanishName = URIish.getHumanishName("/a/b/c.git");
		assertEquals("c"
	}

	public void testGetValidWithSlashesDotGitSlashHumanishName() {
		String humanishName = URIish.getHumanishName("/a/b/c.git/");
		assertEquals("c"
	}

