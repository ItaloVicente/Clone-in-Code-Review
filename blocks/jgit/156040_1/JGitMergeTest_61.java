	private static final String SOURCE_GIT = "source/source";

	@Test
	public void testMergeFastForwardSuccessful() throws IOException {
		final File parentFolder = createTempDirectory();

		final File gitSource = new File(parentFolder
		final Git origin = new CreateRepository(gitSource).execute().get();

		new Commit(origin
				new HashMap<String
					{
						put("file1.txt"
					}
				}).execute();

		new CreateBranch((GitImpl) origin

		new Commit(origin
				new HashMap<String
					{
						put("file2.txt"
					}
				}).execute();

		new Commit(origin
				new HashMap<String
					{
						put("file3.txt"
					}
				}).execute();

		new Commit(origin
				new HashMap<String
					{
						put("file4.txt"
					}
				}).execute();

		new Commit(origin
				new HashMap<String
					{
						put("file5.txt"
					}
				}).execute();

		new Merge(origin

		final List<DiffEntry> result = new ListDiffs(origin
				new GetTreeFromRef(origin

		assertThat(result).isEmpty();
	}

	@Test
	public void testMergeNonFastForwardSuccessful() throws IOException {
		final File parentFolder = createTempDirectory();

		final File gitSource = new File(parentFolder
		final Git origin = new CreateRepository(gitSource).execute().get();

		new Commit(origin
				new HashMap<String
					{
						put("file1.txt"
					}
				}).execute();

		new CreateBranch((GitImpl) origin

		new Commit(origin
				new HashMap<String
					{
						put("file2.txt"
					}
				}).execute();

		new Commit(origin
				new HashMap<String
					{
						put("file3.txt"
					}
				}).execute();

		new Commit(origin
				new HashMap<String
					{
						put("file4.txt"
					}
				}).execute();

		new Commit(origin
				new HashMap<String
					{
						put("file5.txt"
					}
				}).execute();

		new Merge(origin

		final List<DiffEntry> result = new ListDiffs(origin
				new GetTreeFromRef(origin

		assertThat(result).isEmpty();
	}

	@Test
	public void testMergeNoDiff() throws IOException {
		final File parentFolder = createTempDirectory();

		final File gitSource = new File(parentFolder
		final Git origin = new CreateRepository(gitSource).execute().get();

		new Commit(origin
				new HashMap<String
					{
						put("file1.txt"
					}
				}).execute();

		new CreateBranch((GitImpl) origin

		new Commit(origin
				new HashMap<String
					{
						put("file1.txt"
					}
				}).execute();

		List<String> commitIds = new Merge(origin

		assertThat(commitIds).isEmpty();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testParametersNotNull() throws IOException {

		new Merge(null
	}

	@Test(expected = GitException.class)
	public void testTryToMergeNonexistentBranch() throws IOException {
		final File parentFolder = createTempDirectory();

		final File gitSource = new File(parentFolder
		final Git origin = new CreateRepository(gitSource).execute().get();

		new Commit(origin
				new HashMap<String
					{
						put("file1.txt"
					}
				}).execute();

		new CreateBranch((GitImpl) origin

		new Commit(origin
				new HashMap<String
					{
						put("file2.txt"
					}
				}).execute();

		new Commit(origin
				new HashMap<String
					{
						put("file3.txt"
					}
				}).execute();

		new Commit(origin
				new HashMap<String
					{
						put("file4.txt"
					}
				}).execute();

		new Commit(origin
				new HashMap<String
					{
						put("file5.txt"
					}
				}).execute();

		new Merge(origin
	}

	@Test(expected = GitException.class)
	public void testMergeBinaryInformationButHasConflicts() throws IOException {

		final byte[] contentA = this.loadImage("images/drools.png");
		final byte[] contentB = this.loadImage("images/jbpm.png");
		final byte[] contentC = this.loadImage("images/opta.png");

		final File parentFolder = createTempDirectory();

		final File gitSource = new File(parentFolder
		final Git origin = new CreateRepository(gitSource).execute().get();

		new Commit(origin
				new HashMap<String
					{
						put("file1.jpg"
					}
				}).execute();

		new CreateBranch((GitImpl) origin

		new Commit(origin
				new HashMap<String
					{
						put("file1.jpg"
					}
				}).execute();

		new Commit(origin
				new HashMap<String
					{
						put("file1.jpg"
					}
				}).execute();

		new Merge(origin

		final List<DiffEntry> result = new ListDiffs(origin
				new GetTreeFromRef(origin

		assertThat(result).isEmpty();
	}

	@Test
	public void testMergeBinaryInformationSuccessful() throws IOException {

		final byte[] contentA = this.loadImage("images/drools.png");
		final byte[] contentB = this.loadImage("images/jbpm.png");

		final File parentFolder = createTempDirectory();

		final File gitSource = new File(parentFolder
		final Git origin = new CreateRepository(gitSource).execute().get();

		new Commit(origin
				new HashMap<String
					{
						put("file1.jpg"
					}
				}).execute();

		new CreateBranch((GitImpl) origin

		new Commit(origin
				new HashMap<String
					{
						put("file1.jpg"
					}
				}).execute();

		new Merge(origin

		final List<DiffEntry> result = new ListDiffs(origin
				new GetTreeFromRef(origin

		assertThat(result).isEmpty();
	}
