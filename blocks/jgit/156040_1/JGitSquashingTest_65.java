	private Logger logger = LoggerFactory.getLogger(JGitSquashingTest.class);

	static {
		CredentialsProvider.setDefault(new UsernamePasswordCredentialsProvider("guest"
	}

	@Test
	public void testSquash4Of5Commits() throws IOException

		final File parentFolder = createTempDirectory();
		logger.info(">> Parent Forlder for the Test: " + parentFolder.getAbsolutePath());
		final File gitFolder = new File(parentFolder

		final Git origin = new CreateRepository(gitFolder).execute().get();

		new Commit(origin
				new HashMap<String
					{
						put("path/to/file1.txt"
					}
				}).execute();
		new Commit(origin
				new HashMap<String
					{
						put("path/to/file2.txt"
					}
				}).execute();
		Iterable<RevCommit> logs = ((GitImpl) origin)._log().setMaxCount(1).all().call();
		RevCommit secondCommit = logs.iterator().next();

		new Commit(origin
				new HashMap<String
					{
						put("path/to/file1.txt"
					}
				}).execute();

		new Commit(origin
				new HashMap<String
					{
						put("path/to/file2.txt"
					}
				}).execute();
		new Commit(origin
				new HashMap<String
					{
						put("path/to/file3.txt"
					}
				}).execute();
		logs = ((GitImpl) origin)._log().all().call();
		int commitsCount = 0;
		for (RevCommit commit : logs) {
			logger.info(">>> Origin Commit: " + commit.getFullMessage() + " - " + commit.toString());
			commitsCount++;
		}
		assertThat(commitsCount).isEqualTo(5);

		assertThat(origin.getPathInfo("master"
		assertThat(origin.getPathInfo("master"
		assertThat(origin.getPathInfo("master"
		assertThat(origin.getPathInfo("master"
		assertThat(origin.getPathInfo("master"

		logger.info("Squashing from " + secondCommit.getName() + "  to HEAD");
		new Squash((GitImpl) origin

		commitsCount = 0;
		for (RevCommit commit : ((GitImpl) origin)._log().all().call()) {
			logger.info(">>> Final Commit: " + commit.getFullMessage() + " - " + commit.toString());
			commitsCount++;
		}
		assertThat(commitsCount).isEqualTo(2);
	}

	@Test
	public void testFailWhenTryToSquashCommitsFromDifferentBranches() throws IOException

		final File parentFolder = createTempDirectory();
		logger.info(">> Parent Forlder for the Test: " + parentFolder.getAbsolutePath());
		final File gitFolder = new File(parentFolder

		final Git origin = new CreateRepository(gitFolder).execute().get();

		new Commit(origin
				new HashMap<String
					{
						put("path/to/file1.txt"
					}
				}).execute();
		new Commit(origin
				new HashMap<String
					{
						put("path/to/file2.txt"
					}
				}).execute();
		new Commit(origin
				new HashMap<String
					{
						put("path/to/file3.txt"
					}
				}).execute();
		new Commit(origin
				new HashMap<String
					{
						put("path/to/file4.txt"
					}
				}).execute();

		List<RevCommit> masterCommits = getCommitsFromBranch((GitImpl) origin
		List<RevCommit> developCommits = getCommitsFromBranch((GitImpl) origin

		assertThat(masterCommits.size()).isEqualTo(3);
		assertThat(developCommits.size()).isEqualTo(1);

		try {
			new Squash((GitImpl) origin
			fail("If it reaches here the test has failed because he found the commit into the branch");
		} catch (GitException e) {
			logger.info(e.getMessage());
			assertThat(e).isNotNull();
		}
	}

	private List<RevCommit> getCommitsFromBranch(final GitImpl origin
			throws GitAPIException
		List<RevCommit> commits = new ArrayList<>();
		final ObjectId id = new GetRef(origin.getRepository()
		for (RevCommit commit : origin._log().add(id).call()) {
			logger.info(">>> " + branch + " Commits: " + commit.getFullMessage() + " - " + commit.toString());
			commits.add(commit);
		}
		return commits;
	}

	@Test
	public void testSquashCommitsWithDifferentPaths() throws IOException

		final File parentFolder = createTempDirectory();
		logger.info(">> Parent Folder for the Test: " + parentFolder.getAbsolutePath());
		final File gitFolder = new File(parentFolder

		final Git origin = new CreateRepository(gitFolder).execute().get();

		new Commit(origin
				new HashMap<String
					{
						put("file1.txt"
					}
				}).execute();
		new Commit(origin
				new HashMap<String
					{
						put("path/to/file2.txt"
					}
				}).execute();
		Iterable<RevCommit> logs = ((GitImpl) origin)._log().setMaxCount(1).all().call();
		RevCommit secondCommit = logs.iterator().next();

		new Commit(origin
				new HashMap<String
					{
						put("file1.txt"
					}
				}).execute();

		new Commit(origin
				new HashMap<String
					{
						put("path/to/file2.txt"
					}
				}).execute();
		new Commit(origin
				new HashMap<String
					{
						put("path/file3.txt"
					}
				}).execute();

		for (RevCommit commit : ((GitImpl) origin)._log().all().call()) {
			logger.info(">>> Origin Commit: " + commit.getFullMessage() + " - " + commit.toString());
		}

		assertThat(origin.getPathInfo("master"
		assertThat(origin.getPathInfo("master"
		assertThat(origin.getPathInfo("master"
		assertThat(origin.getPathInfo("master"
		assertThat(origin.getPathInfo("master"

		logger.info("Squashing from " + secondCommit.getName() + "  to HEAD");
		new Squash((GitImpl) origin

		int commitsCount = 0;
		for (RevCommit commit : ((GitImpl) origin)._log().all().call()) {
			logger.info(">>> Final Commit: " + commit.getFullMessage() + " - " + commit.toString());
			commitsCount++;
		}

		assertThat(commitsCount).isEqualTo(2);
	}
