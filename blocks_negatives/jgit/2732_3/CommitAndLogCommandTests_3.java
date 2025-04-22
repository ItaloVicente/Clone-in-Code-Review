
	@SuppressWarnings("unused")
	private File prepare_f1_1(final Git git) throws IOException {
		return writeTrashFile(F1, "c1");
	}

	private File prepare_f1_2(final Git git) throws Exception {
		final File f1 = prepare_f1_4(git);
		f1.delete();
		return f1;
	}

	private File prepare_f1_3(final Git git) throws Exception {
		final File f1 = prepare_f1_7(git);
		git.rm().addFilepattern(F1).call();
		return f1;
	}

	private File prepare_f1_4(final Git git) throws Exception {
		final File f1 = prepare_f1_1(git);
		git.add().addFilepattern(F1).call();
		return f1;
	}

	private File prepare_f1_5(final Git git) throws Exception {
		final File f1 = prepare_f1_7(git);
		f1.delete();
		return f1;
	}

	private File prepare_f1_6(final Git git) throws Exception {
		final File f1 = prepare_f1_3(git);
		write(f1, "c1");
		return f1;
	}

	private File prepare_f1_7(final Git git) throws Exception {
		final File f1 = prepare_f1_4(git);
		git.commit().setOnly(F1).setMessage(MSG).call();
		return f1;
	}

	private File prepare_f1_8(final Git git) throws Exception {
		final File f1 = prepare_f1_4(git);
		write(f1, "c1'");
		return f1;
	}

	private File prepare_f1_9(final Git git) throws Exception {
		final File f1 = prepare_f1_3(git);
		write(f1, "c1'");
		return f1;
	}

	private File prepare_f1_10(final Git git) throws Exception {
		final File f1 = prepare_f1_9(git);
		git.add().addFilepattern(F1).call();
		f1.delete();
		return f1;
	}

	private File prepare_f1_11(final Git git) throws Exception {
		final File f1 = prepare_f1_7(git);
		write(f1, "c1'");
		return f1;
	}

	private File prepare_f1_12(final Git git) throws Exception {
		final File f1 = prepare_f1_13(git);
		write(f1, "c1");
		return f1;
	}

	private File prepare_f1_13(final Git git) throws Exception {
		final File f1 = prepare_f1_11(git);
		git.add().addFilepattern(F1).call();
		return f1;
	}

	private File prepare_f1_14(final Git git) throws Exception {
		final File f1 = prepare_f1_13(git);
		write(f1, "c1''");
		return f1;
	}

	@SuppressWarnings("null")
	private void executeAndCheck_f1_1(final Git git, final int state)
			throws Exception {
		JGitInternalException exception = null;
		try {
			git.commit().setOnly(F1).setMessage(MSG).call();
		} catch (JGitInternalException e) {
			exception = e;
		}
		assertNotNull(exception);
		assertTrue(exception.getMessage().contains(F1));

		assertEquals(expected_f3_head(state), getHead(git, F3));
		assertEquals(expected_f3_idx(state), indexState(CONTENT));
	}

	@SuppressWarnings("null")
	private void executeAndCheck_f1_1_f2_f14(final Git git, final int state)
			throws Exception {
		JGitInternalException exception = null;
		try {
			git.commit().setOnly(F1).setOnly(F2).setMessage(MSG).call();
		} catch (JGitInternalException e) {
			exception = e;
		}
		assertNotNull(exception);
		assertTrue(exception.getMessage().contains(F1));

		assertEquals("c2", getHead(git, F2));
		assertEquals(expected_f3_head(state), getHead(git, F3));
		assertEquals("[f2.txt, mode:100644, content:c2']"
				+ expected_f3_idx(state), indexState(CONTENT));
	}

	@SuppressWarnings("null")
	private void executeAndCheck_f1_2(final Git git, final int state)
			throws Exception {
		JGitInternalException exception = null;
		try {
			git.commit().setOnly(F1).setMessage(MSG).call();
		} catch (JGitInternalException e) {
			exception = e;
		}
		assertNotNull(exception);
		assertTrue(exception.getMessage().contains("No changes"));

		assertEquals(expected_f3_head(state), getHead(git, F3));
		assertEquals("[f1.txt, mode:100644, content:c1]"
				+ expected_f3_idx(state), indexState(CONTENT));
	}

	private void executeAndCheck_f1_2_f2_f14(final Git git, final int state)
			throws Exception {
		git.commit().setOnly(F1).setOnly(F2).setMessage(MSG).call();

		assertEquals("", getHead(git, F1));
		assertEquals("c2''", getHead(git, F2));
		assertEquals(expected_f3_head(state), getHead(git, F3));
		assertEquals("[f2.txt, mode:100644, content:c2'']"
				+ expected_f3_idx(state), indexState(CONTENT));
	}

	private void executeAndCheck_f1_3(final Git git, final int state)
			throws Exception {
		git.commit().setOnly(F1).setMessage(MSG).call();

		assertEquals("", getHead(git, F1));
		assertEquals(expected_f3_head(state), getHead(git, F3));
		assertEquals(expected_f3_idx(state), indexState(CONTENT));
	}

	private void executeAndCheck_f1_4(final Git git, final int state)
			throws Exception {
		git.commit().setOnly(F1).setMessage(MSG).call();

		assertEquals("c1", getHead(git, F1));
		assertEquals(expected_f3_head(state), getHead(git, F3));
		assertEquals("[f1.txt, mode:100644, content:c1]"
				+ expected_f3_idx(state), indexState(CONTENT));
	}

	private void executeAndCheck_f1_5(final Git git, final int state)
			throws Exception {
		executeAndCheck_f1_3(git, state);
	}

	@SuppressWarnings("null")
	private void executeAndCheck_f1_6(final Git git, final int state)
			throws Exception {
		JGitInternalException exception = null;
		try {
			git.commit().setOnly(F1).setMessage(MSG).call();
		} catch (JGitInternalException e) {
			exception = e;
		}
		assertNotNull(exception);
		assertTrue(exception.getMessage().contains("No changes"));

		assertEquals(expected_f3_head(state), getHead(git, F3));
		assertEquals(expected_f3_idx(state), indexState(CONTENT));
	}

	private void executeAndCheck_f1_6_f2_14(final Git git, final int state)
			throws Exception {
		git.commit().setOnly(F1).setOnly(F2).setMessage(MSG).call();

		assertEquals("c1", getHead(git, F1));
		assertEquals("c2''", getHead(git, F2));
		assertEquals(expected_f3_head(state), getHead(git, F3));
		assertEquals("[f1.txt, mode:100644, content:c1]"
				+ "[f2.txt, mode:100644, content:c2'']"
				+ expected_f3_idx(state), indexState(CONTENT));
	}

	private void executeAndCheck_f1_7(final Git git, final int state)
			throws Exception {
		executeAndCheck_f1_2(git, state);
	}

	private void executeAndCheck_f1_7_f2_14(final Git git, final int state)
			throws Exception {
		executeAndCheck_f1_6_f2_14(git, state);
	}

	private void executeAndCheck_f1_8(final Git git, final int state)
			throws Exception {
		git.commit().setOnly(F1).setMessage(MSG).call();

		assertEquals("c1'", getHead(git, F1));
		assertEquals(expected_f3_head(state), getHead(git, F3));
		assertEquals("[f1.txt, mode:100644, content:c1']"
				+ expected_f3_idx(state), indexState(CONTENT));
	}

	private void executeAndCheck_f1_9(final Git git, final int state)
			throws Exception {
		executeAndCheck_f1_8(git, state);
	}

	private void executeAndCheck_f1_10(final Git git, final int state)
			throws Exception {
		executeAndCheck_f1_3(git, state);
	}

	private void executeAndCheck_f1_11(final Git git, final int state)
			throws Exception {
		executeAndCheck_f1_8(git, state);
	}

	@SuppressWarnings("null")
	private void executeAndCheck_f1_12(final Git git, final int state)
			throws Exception {
		JGitInternalException exception = null;
		try {
			git.commit().setOnly(F1).setMessage(MSG).call();
		} catch (JGitInternalException e) {
			exception = e;
		}
		assertNotNull(exception);
		assertTrue(exception.getMessage().contains("No changes"));

		assertEquals(expected_f3_head(state), getHead(git, F3));
		assertEquals("[f1.txt, mode:100644, content:c1']"
				+ expected_f3_idx(state), indexState(CONTENT));
	}

	private void executeAndCheck_f1_12_f2_14(final Git git, final int state)
			throws Exception {
		executeAndCheck_f1_6_f2_14(git, state);
	}

	private void executeAndCheck_f1_13(final Git git, final int state)
			throws Exception {
		executeAndCheck_f1_8(git, state);
	}

	private void executeAndCheck_f1_14(final Git git, final int state)
			throws Exception {
		git.commit().setOnly(F1).setMessage(MSG).call();

		assertEquals("c1''", getHead(git, F1));
		assertEquals(expected_f3_head(state), getHead(git, F3));
		assertEquals("[f1.txt, mode:100644, content:c1'']"
				+ expected_f3_idx(state), indexState(CONTENT));
	}

	private void prepare_f3(final Git git, final int state) throws Exception {
		prepare_f3_f2_14(git, state, false);
	}

	private void prepare_f3_f2_14(final Git git, final int state)
			throws Exception {
		prepare_f3_f2_14(git, state, true);
	}

	private void prepare_f3_f2_14(final Git git, final int state,
			final boolean include_f2) throws Exception {
		File f2 = null;
		if (include_f2) {
			f2 = writeTrashFile(F2, "c2");
			git.add().addFilepattern(F2).call();
			git.commit().setMessage(MSG).call();
		}

		if (state >= 1) {
			writeTrashFile(F3, "c3");
			git.add().addFilepattern(F3).call();
		}
		if (state >= 2)
			git.commit().setMessage(MSG).call();
		if (state >= 3)
			git.rm().addFilepattern(F3).call();
		if (state == 4) {
			writeTrashFile(F3, "c3'");
			git.add().addFilepattern(F3).call();
		}

		if (include_f2) {
			write(f2, "c2'");
			git.add().addFilepattern(F2).call();
			write(f2, "c2''");
		}
	}

	private String expected_f3_head(final int state) {
		switch (state) {
		case 0:
		case 1:
			return "";
		case 2:
		case 3:
		case 4:
			return "c3";
		}
		return null;
	}

	private String expected_f3_idx(final int state) {
		switch (state) {
		case 0:
		case 3:
			return "";
		case 1:
		case 2:
			return "[f3.txt, mode:100644, content:c3]";
		case 4:
			return "[f3.txt, mode:100644, content:c3']";
		}
		return null;
	}

	private String getHead(final Git git, final String path) throws Exception {
		try {
			final Repository repo = git.getRepository();
			final ObjectId headId = repo.resolve(Constants.HEAD + "^{commit}");
			final TreeWalk tw = TreeWalk.forPath(repo, path,
					new RevWalk(repo).parseTree(headId));
			return new String(tw.getObjectReader().open(tw.getObjectId(0))
					.getBytes());
		} catch (Exception e) {
			return "";
		}
	}
