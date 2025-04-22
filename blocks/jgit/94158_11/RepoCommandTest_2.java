
	void testRelative(String a
		String got = RepoCommand.relativize(URI.create(a)

		if (!got.equals(want)) {
			fail(String.format("relative('%s'
		}
	}

	@Test
	public void relative() {
		testRelative("a/b/"
		testRelative("a/b"
		testRelative("a/"
		testRelative("a/"
		testRelative("/a/b/c"
		testRelative("/abc"
		testRelative("abc"
	}
