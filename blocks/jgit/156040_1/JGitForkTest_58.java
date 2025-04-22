	private static final String TARGET_GIT = "target/target"
	private static Logger logger = LoggerFactory.getLogger(JGitForkTest.class);

	@Test
	public void testToForkSuccess() throws IOException
		final File parentFolder = createTempDirectory();

		final File gitSource = new File(parentFolder
		final Git origin = new CreateRepository(gitSource).execute().get();

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

		new Fork(parentFolder

		final File gitCloned = new File(parentFolder
		final Git cloned = Git.createRepository(gitCloned);

		assertThat(cloned).isNotNull();

		assertThat(new ListRefs(cloned.getRepository()).execute()).hasSize(2);

		assertThat(new ListRefs(cloned.getRepository()).execute().get(0).getName()).isEqualTo("refs/heads/master");
		assertThat(new ListRefs(cloned.getRepository()).execute().get(1).getName()).isEqualTo("refs/heads/user_branch");

		final String remotePath = new File(((GitImpl) cloned)._remoteList().call().get(0).getURIs().get(0).getPath())
				.getAbsolutePath();
		assertThat(remotePath).isEqualTo(new File(gitSource.getPath()).getAbsolutePath());
	}

	@Test(expected = GitException.class)
	public void testToForkAlreadyExists() throws IOException
		final File parentFolder = createTempDirectory();

		final File gitSource = new File(parentFolder
		final Git origin = new CreateRepository(gitSource).execute().get();

		new Commit(origin
				new HashMap<String
					{
						put("file.txt"
					}
				}).execute();

		final File gitTarget = new File(parentFolder
		final Git originTarget = new CreateRepository(gitTarget).execute().get();

		new Commit(originTarget
				new HashMap<String
					{
						put("file.txt"
					}
				}).execute();

		new Fork(parentFolder
	}

	@Test
	public void testToForkWrongSource() throws IOException {
		final File parentFolder = createTempDirectory();

		try {
			new Fork(parentFolder
					.execute();
			fail("If got here is because it could for the repository");
		} catch (Clone.CloneException e) {
			assertThat(e).isNotNull();
			logger.info(e.getMessage()
		}
	}

	@Test
	public void testForkRepository() throws GitAPIException

		String SOURCE = "testforkA/source";
		String TARGET = "testforkB/target";

		final Map<String
			{
				put(JGitFileSystemProviderConfiguration.GIT_ENV_KEY_INIT
			}
		};

		final URI sourceUri = URI.create(sourcePath);
		provider.newFileSystem(sourceUri

		final Map<String
			{
				put(JGitFileSystemProviderConfiguration.GIT_ENV_KEY_DEFAULT_REMOTE_NAME
			}
		};
		final URI forkUri = URI.create(forkPath);
		final JGitFileSystem fs = (JGitFileSystem) provider.newFileSystem(forkUri

		assertThat(((GitImpl) fs.getGit())._remoteList().call().get(0).getURIs().get(0).toString())
				.isEqualTo(new File(provider.getGitRepoContainerDir()
	}

	@Test(expected = FileSystemAlreadyExistsException.class)
	public void testForkRepositoryThatAlreadyExists() throws IOException {

		String SOURCE = "testforkA/source";
		String TARGET = "testforkB/target";

		final Map<String
			{
				put(JGitFileSystemProviderConfiguration.GIT_ENV_KEY_INIT
			}
		};

		final URI sourceUri = URI.create(sourcePath);
		provider.newFileSystem(sourceUri

		final Map<String
			{
				put(JGitFileSystemProviderConfiguration.GIT_ENV_KEY_DEFAULT_REMOTE_NAME
			}
		};

		final URI forkUri = URI.create(forkPath);
		provider.newFileSystem(forkUri
		provider.newFileSystem(forkUri
	}

	@Test
	public void testForkWithoutHookDirShouldNotBeUpdatedAfterGitHookDirAdded() throws IOException

		final File hooksDir = createTempDirectory();

		final File parentFolder = createTempDirectory();

		final File gitSource = new File(parentFolder

		writeMockHook(hooksDir
		writeMockHook(hooksDir

		final Git repo = new CreateRepository(gitSource
		final Git existentRepoWithHookDirDefined = new CreateRepository(gitSource

		File[] hooks = new File(existentRepoWithHookDirDefined.getRepository().getDirectory()
		assertThat(hooks).isEmpty();
	}

	@Test
	public void testForkWithHookDir() throws IOException
		final File hooksDir = createTempDirectory();

		writeMockHook(hooksDir
		writeMockHook(hooksDir

		final File parentFolder = createTempDirectory();

		final File gitSource = new File(parentFolder
		final Git origin = new CreateRepository(gitSource

		new Commit(origin
				new HashMap<String
					{
						put("file2.txt"
					}
				}).execute();

		final Git cloned = new Fork(parentFolder
				hooksDir).execute();

		assertThat(cloned).isNotNull();

		assertThat(new ListRefs(cloned.getRepository()).execute()).hasSize(1);

		assertThat(new ListRefs(cloned.getRepository()).execute().get(0).getName()).isEqualTo("refs/heads/user_branch");

		final String remotePath = new File(((GitImpl) cloned)._remoteList().call().get(0).getURIs().get(0).getPath())
				.getAbsolutePath();
		assertThat(remotePath).isEqualTo(new File(gitSource.getPath()).getAbsolutePath());

		boolean foundPreCommitHook = false;
		boolean foundPostCommitHook = false;
		File[] hooks = new File(cloned.getRepository().getDirectory()
		assertThat(hooks).isNotEmpty().isNotNull();
		assertThat(hooks.length).isEqualTo(2);
		for (File hook : hooks) {
			if (hook.getName().equals(PreCommitHook.NAME)) {
				foundPreCommitHook = hook.canExecute();
			} else if (hook.getName().equals(PostCommitHook.NAME)) {
				foundPostCommitHook = hook.canExecute();
			}
		}
		assertThat(foundPreCommitHook).isTrue();
		assertThat(foundPostCommitHook).isTrue();
	}

	@Test
	public void testForkMultipleBranches() throws Exception {
		final File parentFolder = createTempDirectory();

		final File gitSource = new File(parentFolder

		final Git origin = new CreateRepository(gitSource

		commit(origin
				content("file3.txt"

		branch(origin
		commit(origin

		branch(origin
		commit(origin

		final Git cloned = new Fork(parentFolder
				CredentialsProvider.getDefault()

		assertThat(cloned).isNotNull();
		final Set<String> clonedRefs = listRefs(cloned).stream().map(ref -> ref.getName()).collect(toSet());
		assertThat(clonedRefs).hasSize(2);
		assertThat(clonedRefs).containsExactly("refs/heads/master"
	}
