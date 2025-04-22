				toString(executeUnchecked("git branch master")));
	}

	@Test
	public void testRenameSingleArg() throws Exception {
		try {
			toString(execute("git branch -m"));
			fail("Must die");
		} catch (Die e) {
		}
		String result = toString(execute("git branch -m slave"));
		assertEquals(""
		result = toString(execute("git branch -a"));
		assertEquals("* slave"
	}

	@Test
	public void testRenameTwoArgs() throws Exception {
		String result = toString(execute("git branch -m master slave"));
		assertEquals(""
		result = toString(execute("git branch -a"));
		assertEquals("* slave"
	}

	@Test
	public void testCreate() throws Exception {
		try {
			toString(execute("git branch a b"));
			fail("Must die");
		} catch (Die e) {
		}
		String result = toString(execute("git branch second"));
		assertEquals(""
		result = toString(execute("git branch"));
		assertEquals(toString("* master"
		result = toString(execute("git branch -v"));
		assertEquals(toString("* master 6fd41be initial commit"
				"second 6fd41be initial commit")
	}

	@Test
	public void testDelete() throws Exception {
		try {
			toString(execute("git branch -d"));
			fail("Must die");
		} catch (Die e) {
		}
		String result = toString(execute("git branch second"));
		assertEquals(""
		result = toString(execute("git branch -d second"));
		assertEquals(""
		result = toString(execute("git branch"));
		assertEquals("* master"
	}

	@Test
	public void testDeleteMultiple() throws Exception {
		String result = toString(execute("git branch second"
				"git branch third"
		assertEquals(""
		result = toString(execute("git branch -d second third fourth"));
		assertEquals(""
		result = toString(execute("git branch"));
		assertEquals("* master"
	}

	@Test
	public void testDeleteForce() throws Exception {
		try {
			toString(execute("git branch -D"));
			fail("Must die");
		} catch (Die e) {
		}
		String result = toString(execute("git branch second"));
		assertEquals(""
		result = toString(execute("git checkout second"));
		assertEquals("Switched to branch 'second'"

		File a = writeTrashFile("a"
		assertTrue(a.exists());
		execute("git add a"

		result = toString(execute("git checkout master"));
		assertEquals("Switched to branch 'master'"

		result = toString(execute("git branch"));
		assertEquals(toString("* master"

		try {
			toString(execute("git branch -d second"));
			fail("Must die");
		} catch (Die e) {
		}
		result = toString(execute("git branch -D second"));
		assertEquals(""

		result = toString(execute("git branch"));
		assertEquals("* master"
	}

	@Test
	public void testDeleteForceMultiple() throws Exception {
		String result = toString(execute("git branch second"
				"git branch third"

		assertEquals(""
		result = toString(execute("git checkout second"));
		assertEquals("Switched to branch 'second'"

		File a = writeTrashFile("a"
		assertTrue(a.exists());
		execute("git add a"

		result = toString(execute("git checkout master"));
		assertEquals("Switched to branch 'master'"

		result = toString(execute("git branch"));
		assertEquals(toString("fourth"

		try {
			toString(execute("git branch -d second third fourth"));
			fail("Must die");
		} catch (Die e) {
		}
		result = toString(execute("git branch"));
		assertEquals(toString("fourth"

		result = toString(execute("git branch -D second third fourth"));
		assertEquals(""

		result = toString(execute("git branch"));
		assertEquals("* master"
	}

	@Test
	public void testCreateFromOldCommit() throws Exception {
		File a = writeTrashFile("a"
		assertTrue(a.exists());
		execute("git add a"
		File b = writeTrashFile("b"
		assertTrue(b.exists());
		execute("git add b"
		String result = toString(execute("git log -n 1 --reverse"));
		String firstCommitId = result.substring("commit ".length()
				result.indexOf('\n'));

		result = toString(execute("git branch -f second " + firstCommitId));
		assertEquals(""

		result = toString(execute("git branch"));
		assertEquals(toString("* master"

		result = toString(execute("git checkout second"));
		assertEquals("Switched to branch 'second'"
		assertFalse(b.exists());
