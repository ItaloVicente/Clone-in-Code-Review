		final File gitSource = new File(parentFolder

		final File gitTarget = new File(parentFolder

		final Git origin = setupGitRepo(gitSource

		final Git cloned = new Clone(gitTarget
				CredentialsProvider.getDefault()

		assertThat(cloned).isNotNull();

		assertThat(new ListRefs(cloned.getRepository()).execute()).hasSize(2);
		assertEquals(new ListRefs(cloned.getRepository()).execute().size()
				new ListRefs(origin.getRepository()).execute().size());

		assertThat(new ListRefs(cloned.getRepository()).execute().get(0).getName()).isEqualTo("refs/heads/master");
		assertThat(new ListRefs(cloned.getRepository()).execute().get(1).getName()).isEqualTo("refs/heads/user_branch");
	}

	@Test
	public void cloneShouldOnlyWorksWithEmptyRepos() throws IOException {
		final File parentFolder = createTempDirectory();

		final File gitSource = new File(parentFolder

		final File gitTarget = new File(parentFolder

		final Git origin = setupGitRepo(gitSource

		final Git cloned = new Clone(gitTarget
				CredentialsProvider.getDefault()

		assertThat(cloned).isNotNull();

		assertThat(new ListRefs(cloned.getRepository()).execute()).hasSize(2);
		assertEquals(new ListRefs(cloned.getRepository()).execute().size()
				new ListRefs(origin.getRepository()).execute().size());

		assertThatThrownBy(() -> new Clone(gitTarget
				CredentialsProvider.getDefault()
						.isInstanceOf(Clone.CloneException.class);
	}

	@Test
	public void testCloneWithHookDir() throws IOException
		final File hooksDir = createTempDirectory();

		writeMockHook(hooksDir
		writeMockHook(hooksDir

		final File parentFolder = createTempDirectory();

		final File gitSource = new File(parentFolder

		final File gitTarget = new File(parentFolder

		final Git origin = setupGitRepo(gitSource

		final Git cloned = new Clone(gitTarget
				CredentialsProvider.getDefault()

		assertThat(cloned).isNotNull();

		assertThat(new ListRefs(cloned.getRepository()).execute()).hasSize(2);
		assertEquals(new ListRefs(cloned.getRepository()).execute().size()
				new ListRefs(origin.getRepository()).execute().size());

		assertThat(new ListRefs(cloned.getRepository()).execute().get(0).getName()).isEqualTo("refs/heads/master");
		assertThat(new ListRefs(cloned.getRepository()).execute().get(1).getName()).isEqualTo("refs/heads/user_branch");

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

	private Git setupGitRepo(File gitSource
		final Git origin = new CreateRepository(gitSource

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
		return origin;
	}

	@Test
	public void cloneNotMirrorRepoConfigTest() throws IOException {
		final File parentFolder = createTempDirectory();

		final File gitSource = new File(parentFolder

		final File gitTarget = new File(parentFolder

		final Git origin = setupGitRepo(gitSource

		boolean isMirror = false;
		boolean sslVerify = true;
		final Git clonedNotMirror = new Clone(gitTarget
				CredentialsProvider.getDefault()

		assertThat(clonedNotMirror).isNotNull();

		StoredConfig config = clonedNotMirror.getRepository().getConfig();

		assertNotEquals(Clone.REFS_MIRRORED
		assertNull(config.getString("remote"
		assertEquals(gitSource.getAbsolutePath()

		boolean missingDefaultValue = true;
		assertEquals(missingDefaultValue
	}

	@Test
	public void cloneMirrorRepoNoSSLVerifyConfigTest() throws IOException {
		final File parentFolder = createTempDirectory();

		final File gitSource = new File(parentFolder

		final File gitTarget = new File(parentFolder

		final Git origin = setupGitRepo(gitSource

		assertTrue(provider.config.isSslVerify());

		boolean isMirror = true;
		boolean sslVerify = false;
		final Git clonedMirror = new Clone(gitTarget
				CredentialsProvider.getDefault()

		assertThat(clonedMirror).isNotNull();

		StoredConfig config = clonedMirror.getRepository().getConfig();

		assertEquals(Clone.REFS_MIRRORED
		assertNull(config.getString("remote"
		assertEquals(gitSource.getAbsolutePath()
		assertEquals(sslVerify
	}

	@Test
	public void testCloneMultipleBranches() throws Exception {
		final File parentFolder = createTempDirectory();

		final File gitSource = new File(parentFolder

		final File gitTarget = new File(parentFolder

		final Git origin = setupGitRepo(gitSource

		commit(origin
				content("file3.txt"

		branch(origin
		commit(origin

		branch(origin
		commit(origin

		final Git cloned = new Clone(gitTarget
				CredentialsProvider.getDefault()

		assertThat(cloned).isNotNull();
		final Set<String> clonedRefs = listRefs(cloned).stream().map(ref -> ref.getName()).collect(toSet());
		assertThat(clonedRefs).hasSize(2);
		assertThat(clonedRefs).containsExactly("refs/heads/master"
	}
