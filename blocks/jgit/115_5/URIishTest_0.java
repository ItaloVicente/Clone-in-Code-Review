
	public void testGetNullHumanishName() {
		try {
			new URIish().getHumanishName();
			fail("path must be not null");
		} catch (IllegalArgumentException e) {
		}
	}

	public void testGetEmptyHumanishName() throws URISyntaxException {
		try {
			new URIish(GIT_SCHEME).getHumanishName();
			fail("empty path is useless");
		} catch (IllegalArgumentException e) {
		}
	}

	public void testGetAbsEmptyHumanishName() {
		try {
			new URIish().getHumanishName();
			fail("empty path is useless");
		} catch (IllegalArgumentException e) {
		}
	}

	public void testGetValidWithEmptySlashDotGitHumanishName()
			throws IllegalArgumentException
		String humanishName = new URIish("/a/b/.git").getHumanishName();
		assertEquals("b"
	}

	public void testGetWithSlashDotGitHumanishName() throws URISyntaxException {
		assertEquals(""
	}

	public void testGetTwoSlashesDotGitHumanishName() throws URISyntaxException {
		assertEquals(""
	}

	public void testGetValidHumanishName() throws IllegalArgumentException
			URISyntaxException {
		String humanishName = new URIish(GIT_SCHEME + "abc").getHumanishName();
		assertEquals("abc"
	}

	public void testGetValidSlashHumanishName()
			throws IllegalArgumentException
		String humanishName = new URIish(GIT_SCHEME + "abc/").getHumanishName();
		assertEquals("abc"
	}

	public void testGetSlashValidSlashHumanishName()
			throws IllegalArgumentException
		String humanishName = new URIish("/abc/").getHumanishName();
		assertEquals("abc"
	}

	public void testGetSlashValidSlashDotGitSlashHumanishName()
			throws IllegalArgumentException
		String humanishName = new URIish("/abc/.git").getHumanishName();
		assertEquals("abc"
	}

	public void testGetSlashSlashDotGitSlashHumanishName()
			throws IllegalArgumentException
				.getHumanishName();
		assertEquals("may return an empty humanish name"
	}

	public void testGetSlashesValidSlashHumanishName()
			throws IllegalArgumentException
		String humanishName = new URIish("/a/b/c/").getHumanishName();
		assertEquals("c"
	}

	public void testGetValidDotGitHumanishName()
			throws IllegalArgumentException
		String humanishName = new URIish(GIT_SCHEME + "abc.git")
				.getHumanishName();
		assertEquals("abc"
	}

	public void testGetValidDotGitSlashHumanishName()
			throws IllegalArgumentException
		String humanishName = new URIish(GIT_SCHEME + "abc.git/")
				.getHumanishName();
		assertEquals("abc"
	}

	public void testGetValidWithSlashDotGitHumanishName()
			throws IllegalArgumentException
		String humanishName = new URIish("/abc.git").getHumanishName();
		assertEquals("abc"
	}

	public void testGetValidWithSlashDotGitSlashHumanishName()
			throws IllegalArgumentException
		String humanishName = new URIish("/abc.git/").getHumanishName();
		assertEquals("abc"
	}

	public void testGetValidWithSlashesDotGitHumanishName()
			throws IllegalArgumentException
		String humanishName = new URIish("/a/b/c.git").getHumanishName();
		assertEquals("c"
	}

	public void testGetValidWithSlashesDotGitSlashHumanishName()
			throws IllegalArgumentException
		String humanishName = new URIish("/a/b/c.git/").getHumanishName();
		assertEquals("c"
	}

