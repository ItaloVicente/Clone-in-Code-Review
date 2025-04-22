	@Test
	public void testNewRepo() throws IOException {

		final File parentFolder = createTempDirectory();
		final File gitFolder = new File(parentFolder

		final Git git = new CreateRepository(gitFolder).execute().get();

		assertThat(git).isNotNull();

		assertThat(new ListRefs(git.getRepository()).execute().size()).isEqualTo(0);

		new Commit(git
			{
				put("file.txt"
			}
		}).execute();

		assertThat(new ListRefs(git.getRepository()).execute().size()).isEqualTo(1);
	}

	@Test
	public void testClone() throws IOException
		final File parentFolder = createTempDirectory();
		final File gitFolder = new File(parentFolder

		final Git origin = new CreateRepository(gitFolder).execute().get();

		new Commit(origin
				new HashMap<String
					{
						put("file2.txt"
					}
				}).execute();
		new Commit(origin
				new HashMap<String
					{
						put("file.txt"
					}
				}).execute();
		new Commit(origin
				new HashMap<String
					{
						put("file3.txt"
					}
				}).execute();

		final File gitClonedFolder = new File(parentFolder

		final Git git = new Clone(gitClonedFolder
				CredentialsProvider.getDefault()

		assertThat(git).isNotNull();

		assertThat(new ListRefs(git.getRepository()).execute()).hasSize(2);

		assertThat(new ListRefs(git.getRepository()).execute().get(0).getName()).isEqualTo("refs/heads/master");
		assertThat(new ListRefs(git.getRepository()).execute().get(1).getName()).isEqualTo("refs/heads/user_branch");
	}

	@Test
	public void testPathResolve() throws IOException
		final File parentFolder = createTempDirectory();
		final File gitFolder = new File(parentFolder

		final Git origin = new CreateRepository(gitFolder).execute().get();

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

		final File gitClonedFolder = new File(parentFolder

		final Git git = new Clone(gitClonedFolder
				CredentialsProvider.getDefault()

		assertThat(git.getPathInfo("user_branch"
		assertThat(git.getPathInfo("user_branch"
		assertThat(git.getPathInfo("user_branch"
	}

	@Test
	public void testAmend() throws IOException
		final File parentFolder = createTempDirectory();
		System.out.println("COOL!:" + parentFolder.toString());
		final File gitFolder = new File(parentFolder

		final Git origin = new CreateRepository(gitFolder).execute().get();

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

		final File gitClonedFolder = new File(parentFolder

		final Git git = new Clone(gitClonedFolder
				CredentialsProvider.getDefault()

		assertThat(git.getPathInfo("master"
		assertThat(git.getPathInfo("master"
		assertThat(git.getPathInfo("master"
	}

	@Test
	public void testBuildVersionAttributes() throws Exception {

		final File parentFolder = createTempDirectory();
		final File gitFolder = new File(parentFolder

		final Git git = new CreateRepository(gitFolder).execute().get();

		new Commit(git
				new HashMap<String
					{
						put("path/to/file2.txt"
					}
				}).execute();
		new Commit(git
				new HashMap<String
					{
						put("path/to/file2.txt"
					}
				}).execute();
		new Commit(git
				new HashMap<String
					{
						put("path/to/file2.txt"
					}
				}).execute();
		new Commit(git
				new HashMap<String
					{
						put("path/to/file2.txt"
					}
				}).execute();

		JGitFileSystem jGitFileSystem = mock(JGitFileSystem.class);
		when(jGitFileSystem.getGit()).thenReturn(git);

		final JGitPathImpl path = mock(JGitPathImpl.class);
		when(path.getFileSystem()).thenReturn(jGitFileSystem);
		when(path.getRefTree()).thenReturn("master");
		when(path.getPath()).thenReturn("path/to/file2.txt");

		final VersionAttributes versionAttributes = new JGitVersionAttributeViewImpl(path).readAttributes();

		List<VersionRecord> records = versionAttributes.history().records();
		assertEquals("commit 1"
		assertEquals("commit 2"
		assertEquals("commit 3"
		assertEquals("commit 4"
	}

	@Test
	public void testDiffForFileCreatedInEmptyRepositoryOrBranch() throws Exception {

		final File parentFolder = createTempDirectory();
		final File gitFolder = new File(parentFolder

		final Git git = new CreateRepository(gitFolder).execute().get();

		final ObjectId oldHead = new GetTreeFromRef(git

		new Commit(git
				new HashMap<String
					{
						put("path/to/file.txt"
					}
				}).execute();

		final ObjectId newHead = new GetTreeFromRef(git

		List<DiffEntry> diff = new ListDiffs(git
		assertNotNull(diff);
		assertFalse(diff.isEmpty());
		assertEquals(ChangeType.ADD
		assertEquals("path/to/file.txt"
	}
