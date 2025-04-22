
	@Test
	public void testDepth() throws Exception {
		createInitialCommit();
		createSecondCommit();
		createThirdCommit();

		File gitDir = db.getDirectory();
		String sourceURI = gitDir.toURI().toString();
		File target = createTempDirectory("target");
		String cmd = "git clone --depth 1 " + sourceURI + " "
				+ shellQuote(target.getPath());
		String[] result = execute(cmd);
		assertArrayEquals(new String[] {
				"Cloning into '" + target.getPath() + "'..."

		Git git2 = Git.open(target);
		addRepoToClose(git2.getRepository());

		List<RevCommit> log = StreamSupport
				.stream(git2.log().all().call().spliterator()
				.collect(Collectors.toList());
		assertEquals(1
		RevCommit commit = log.get(0);
		assertEquals(Set.of(commit.getId())
				git2.getRepository().getObjectDatabase().getShallowCommits());
		assertEquals("Third commit"
		assertEquals(0
	}

	@Test
	public void testDepth2() throws Exception {
		createInitialCommit();
		createSecondCommit();
		createThirdCommit();

		File gitDir = db.getDirectory();
		String sourceURI = gitDir.toURI().toString();
		File target = createTempDirectory("target");
		String cmd = "git clone --depth 2 " + sourceURI + " "
				+ shellQuote(target.getPath());
		String[] result = execute(cmd);
		assertArrayEquals(new String[] {
				"Cloning into '" + target.getPath() + "'..."

		Git git2 = Git.open(target);
		addRepoToClose(git2.getRepository());

		List<RevCommit> log = StreamSupport
				.stream(git2.log().all().call().spliterator()
				.collect(Collectors.toList());
		assertEquals(2
		assertEquals(List.of("Third commit"
				.map(RevCommit::getFullMessage).collect(Collectors.toList()));
	}

	@Test
	public void testCloneRepositoryWithShallowSince() throws Exception {
		createInitialCommit();
		tr.tick(30);
		RevCommit secondCommit = createSecondCommit();
		tr.tick(45);
		createThirdCommit();

		File gitDir = db.getDirectory();
		String sourceURI = gitDir.toURI().toString();
		File target = createTempDirectory("target");
		String cmd = "git clone --shallow-since="
				+ Instant.ofEpochSecond(secondCommit.getCommitTime()).toString()
				+ " " + sourceURI + " " + shellQuote(target.getPath());
		String[] result = execute(cmd);
		assertArrayEquals(new String[] {
				"Cloning into '" + target.getPath() + "'..."

		Git git2 = Git.open(target);
		addRepoToClose(git2.getRepository());

		List<RevCommit> log = StreamSupport
				.stream(git2.log().all().call().spliterator()
				.collect(Collectors.toList());
		assertEquals(2
		assertEquals(List.of("Third commit"
				.map(RevCommit::getFullMessage).collect(Collectors.toList()));
	}

	@Test
	public void testCloneRepositoryWithShallowExclude() throws Exception {
		final RevCommit firstCommit = createInitialCommit();
		final RevCommit secondCommit = createSecondCommit();
		createThirdCommit();

		File gitDir = db.getDirectory();
		String sourceURI = gitDir.toURI().toString();
		File target = createTempDirectory("target");
		String cmd = "git clone --shallow-exclude="
				+ firstCommit.getId().getName() + " --shallow-exclude="
				+ secondCommit.getId().getName() + " " + sourceURI + " "
				+ shellQuote(target.getPath());
		String[] result = execute(cmd);
		assertArrayEquals(new String[] {
				"Cloning into '" + target.getPath() + "'..."

		Git git2 = Git.open(target);
		addRepoToClose(git2.getRepository());

		List<RevCommit> log = StreamSupport
				.stream(git2.log().all().call().spliterator()
				.collect(Collectors.toList());
		assertEquals(1
		assertEquals(List.of("Third commit")
				.map(RevCommit::getFullMessage).collect(Collectors.toList()));
	}

