		git = new CreateRepository(gitSource).execute().get();

		commit(git
				content(TXT_FILES.get(0)
				content(TXT_FILES.get(1)
				content(TXT_FILES.get(2)
	}

	@Test
	public void testNoDiffOnlyOneCommit() throws IOException {
		Map<String
				git.getFirstCommit(git.getRef(MASTER_BRANCH)).getName()
				git.getLastCommit(git.getRef(MASTER_BRANCH)).getName());

		assertThat(content).isEmpty();
	}

	@Test
	public void testHasContent() throws IOException {
		commit(git
				content(TXT_FILES.get(4)

		Map<String
				git.getFirstCommit(git.getRef(MASTER_BRANCH)).getName()
				git.getLastCommit(git.getRef(MASTER_BRANCH)).getName());

		assertThat(contents).isNotEmpty();
		assertThat(contents).hasSize(1);
	}

	@Test
	public void testHasContents() throws IOException {
		commit(git
				content(TXT_FILES.get(3)
				content(TXT_FILES.get(4)

		Map<String
				git.getFirstCommit(git.getRef(MASTER_BRANCH)).getName()
				git.getLastCommit(git.getRef(MASTER_BRANCH)).getName());

		assertThat(contents).isNotEmpty();
		assertThat(contents).hasSize(2);
	}

	@Test
	public void testHasDeleteContents() throws IOException {
		new Commit(git
				new HashMap<String
					{
						put(TXT_FILES.get(0)
					}
				}).execute();

		new Commit(git
				new HashMap<String
					{
						put(TXT_FILES.get(1)
					}
				}).execute();

		Map<String
				git.getFirstCommit(git.getRef(MASTER_BRANCH)).getName()
				git.getLastCommit(git.getRef(MASTER_BRANCH)).getName());

		assertThat(contents).isNotEmpty();
		assertThat(contents).hasSize(2);
		contents.values().forEach(v -> assertThat(v).isNull());
	}

	@Test
	public void testWithManyCommitsOneFile() throws IOException {
		commit(git

		commit(git

		commit(git

		Map<String
				git.getFirstCommit(git.getRef(MASTER_BRANCH)).getName()
				git.getLastCommit(git.getRef(MASTER_BRANCH)).getName());

		assertThat(contents).isNotEmpty();
		assertThat(contents).hasSize(1);
	}

	@Test
	public void testWithMiddleCommits() throws IOException {
		commit(git

		RevCommit startCommit = git.getLastCommit(MASTER_BRANCH);

		commit(git
				content(TXT_FILES.get(3)
				content(TXT_FILES.get(4)

		new Commit(git
				new HashMap<String
					{
						put(TXT_FILES.get(1)
					}
				}).execute();

		RevCommit endCommit = git.getLastCommit(MASTER_BRANCH);

		commit(git

		Map<String

		assertThat(contents).isNotEmpty();
		assertThat(contents).hasSize(3);
	}

	@Test(expected = GitException.class)
	public void testWithWrongBranchName() throws IOException {
		git.mapDiffContent("wrong-branch-name"
				git.getLastCommit(git.getRef(MASTER_BRANCH)).getName());
	}

	@Test(expected = GitException.class)
	public void testWithInvalidCommit() throws IOException {
		git.mapDiffContent(MASTER_BRANCH
	}
