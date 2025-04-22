	@Test
	public void testGetFileAttributeView() throws IOException {
		provider.newFileSystem(newRepo


		final OutputStream outStream = provider.newOutputStream(path);
		outStream.write("my cool content".getBytes());
		outStream.close();

		final Path path2 = provider

		final OutputStream outStream2 = provider.newOutputStream(path2);
		outStream2.write("my cool content".getBytes());
		outStream2.close();


		final OutputStream outStream3 = provider.newOutputStream(path3);
		outStream3.write("my cool content".getBytes());
		outStream3.close();

		final JGitVersionAttributeViewImpl attrs = provider.getFileAttributeView(path3
				JGitVersionAttributeViewImpl.class);

		assertThat(attrs.readAttributes().history().records().size()).isEqualTo(1);
		assertThat(attrs.readAttributes().history().records().get(0).uri()).isNotNull();

		assertThat(attrs.readAttributes().isDirectory()).isFalse();
		assertThat(attrs.readAttributes().isRegularFile()).isTrue();
		assertThat(attrs.readAttributes().creationTime()).isNotNull();
		assertThat(attrs.readAttributes().lastModifiedTime()).isNotNull();
		assertThat(attrs.readAttributes().size()).isEqualTo(15L);

		try {
			provider.getFileAttributeView(
					BasicFileAttributeView.class);
			failBecauseExceptionWasNotThrown(NoSuchFileException.class);
		} catch (Exception ignored) {
		}

		assertThat(provider.getFileAttributeView(path3


		final BasicFileAttributeView attrsRoot = provider.getFileAttributeView(rootPath

		assertThat(attrsRoot.readAttributes().isDirectory()).isTrue();
		assertThat(attrsRoot.readAttributes().isRegularFile()).isFalse();
		assertThat(attrsRoot.readAttributes().creationTime()).isNotNull();
		assertThat(attrsRoot.readAttributes().lastModifiedTime()).isNotNull();
		assertThat(attrsRoot.readAttributes().size()).isEqualTo(-1L);

		final Path prRootPath = provider

		final HiddenAttributeView extendedAttrs = provider.getFileAttributeView(prRootPath

		assertThat(extendedAttrs.readAttributes().isDirectory()).isTrue();
		assertThat(extendedAttrs.readAttributes().isRegularFile()).isFalse();
		assertThat(extendedAttrs.readAttributes().isHidden()).isEqualTo(true);
		assertThat(extendedAttrs.readAttributes().size()).isEqualTo(-1L);
		assertThat(extendedAttrs.readAttributes().creationTime()).isNotNull();
		assertThat(extendedAttrs.readAttributes().lastModifiedTime()).isNotNull();
	}

	@Test
	public void testReadAttributes() throws IOException {
		provider.newFileSystem(newRepo


		final OutputStream outStream = provider.newOutputStream(path);
		outStream.write("my cool content".getBytes());
		outStream.close();


		final OutputStream outStream2 = provider.newOutputStream(path2);
		outStream2.write("my cool content".getBytes());
		outStream2.close();


		final OutputStream outStream3 = provider.newOutputStream(path3);
		outStream3.write("my cool content".getBytes());
		outStream3.close();

		final BasicFileAttributes attrs = provider.readAttributes(path3

		assertThat(attrs.isDirectory()).isFalse();
		assertThat(attrs.isRegularFile()).isTrue();
		assertThat(attrs.creationTime()).isNotNull();
		assertThat(attrs.lastModifiedTime()).isNotNull();
		assertThat(attrs.size()).isEqualTo(15L);

		try {
			provider.readAttributes(
					BasicFileAttributes.class);
			failBecauseExceptionWasNotThrown(NoSuchFileException.class);
		} catch (NoSuchFileException ignored) {
		}

		assertThat(provider.readAttributes(path3


		final BasicFileAttributes attrsRoot = provider.readAttributes(rootPath

		assertThat(attrsRoot.isDirectory()).isTrue();
		assertThat(attrsRoot.isRegularFile()).isFalse();
		assertThat(attrsRoot.creationTime()).isNotNull();
		assertThat(attrsRoot.lastModifiedTime()).isNotNull();
		assertThat(attrsRoot.size()).isEqualTo(-1L);
	}

	@Test
	public void testReadAttributesMap() throws IOException {
		provider.newFileSystem(newRepo


		final OutputStream outStream = provider.newOutputStream(path);
		outStream.write("my cool content".getBytes());
		outStream.close();

		final Path path2 = provider

		final OutputStream outStream2 = provider.newOutputStream(path2);
		outStream2.write("my cool content".getBytes());
		outStream2.close();


		final OutputStream outStream3 = provider.newOutputStream(path3);
		outStream3.write("my cool content".getBytes());
		outStream3.close();

		assertThat(provider.readAttributes(path
		assertThat(provider.readAttributes(path
		assertThat(provider.readAttributes(path
		assertThat(provider.readAttributes(path
		assertThat(provider.readAttributes(path
		assertThat(provider.readAttributes(path
		assertThat(provider.readAttributes(path

		assertThat(provider.readAttributes(path
		assertThat(provider.readAttributes(path
		assertThat(provider.readAttributes(path
		assertThat(provider.readAttributes(path

		try {
			provider.readAttributes(path
			failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
		} catch (IllegalArgumentException ignored) {
		}

		try {
			provider.readAttributes(path
			failBecauseExceptionWasNotThrown(UnsupportedOperationException.class);
		} catch (UnsupportedOperationException ignored) {
		}


		assertThat(provider.readAttributes(rootPath
		assertThat(provider.readAttributes(rootPath
		assertThat(provider.readAttributes(rootPath
		assertThat(provider.readAttributes(rootPath
		assertThat(provider.readAttributes(rootPath
				.hasSize(2);
		assertThat(provider.readAttributes(rootPath

		assertThat(provider.readAttributes(rootPath
		assertThat(provider.readAttributes(rootPath
		assertThat(provider.readAttributes(rootPath
		assertThat(provider.readAttributes(rootPath

		try {
			provider.readAttributes(rootPath
			failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
		} catch (IllegalArgumentException ignored) {
		}

		try {
			provider.readAttributes(rootPath
			failBecauseExceptionWasNotThrown(UnsupportedOperationException.class);
		} catch (UnsupportedOperationException ignored) {
		}

		try {
			provider.readAttributes(
					BasicFileAttributes.class);
			failBecauseExceptionWasNotThrown(NoSuchFileException.class);
		} catch (NoSuchFileException ignored) {
		}
	}

	@Test
	public void testSetAttribute() throws IOException {
		provider.newFileSystem(newRepo


		final OutputStream outStream = provider.newOutputStream(path);
		outStream.write("my cool content".getBytes());
		outStream.close();


		final OutputStream outStream2 = provider.newOutputStream(path2);
		outStream2.write("my cool content".getBytes());
		outStream2.close();


		final OutputStream outStream3 = provider.newOutputStream(path3);
		outStream3.write("my cool content".getBytes());
		outStream3.close();

		try {
			provider.setAttribute(path3
			failBecauseExceptionWasNotThrown(UnsupportedOperationException.class);
		} catch (UnsupportedOperationException ignored) {
		}

		try {
			provider.setAttribute(path3
			failBecauseExceptionWasNotThrown(UnsupportedOperationException.class);
		} catch (UnsupportedOperationException ignored) {
		}

		try {
			provider.setAttribute(path3
			failBecauseExceptionWasNotThrown(IllegalStateException.class);
		} catch (IllegalStateException ignored) {
		}

		try {
			provider.setAttribute(path3
			failBecauseExceptionWasNotThrown(UnsupportedOperationException.class);
		} catch (UnsupportedOperationException ignored) {
		}

		try {
			provider.setAttribute(path3
			failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
		} catch (IllegalArgumentException ignored) {
		}
	}

	private static class MyInvalidFileAttributeView implements BasicFileAttributeView {

		@Override
		public BasicFileAttributes readAttributes() throws IOException {
			return null;
		}

		@Override
		public void setTimes(FileTime lastModifiedTime
				throws IOException {

		}

		@Override
		public String name() {
			return null;
		}
	}

	@Test
	public void checkProperAmend() throws Exception {


		final FileSystem fs = provider.newFileSystem(newRepo
			{
				put(JGitFileSystemProviderConfiguration.GIT_ENV_KEY_INIT
			}
		});

		assertThat(fs).isNotNull();

		for (int z = 0; z < 5; z++) {
			final Path _path = provider
			provider.setAttribute(_path
			{
				final Path path = provider
				final OutputStream outStream = provider.newOutputStream(path);
				assertThat(outStream).isNotNull();
				outStream.write(("my cool content" + z).getBytes());
				outStream.close();
			}
			{
				final Path path2 = provider
				final OutputStream outStream2 = provider.newOutputStream(path2);
				assertThat(outStream2).isNotNull();
				outStream2.write(("bad content" + z).getBytes());
				outStream2.close();
			}

			provider.setAttribute(_path
		}

		final JGitVersionAttributeViewImpl attrs = provider.getFileAttributeView(path.getRoot()
				JGitVersionAttributeViewImpl.class);

		assertThat(attrs.readAttributes().history().records().size()).isEqualTo(5);
	}

	@Test
	public void accessOldVersions() throws Exception {


		final FileSystem fs = provider.newFileSystem(newRepo
			{
				put(JGitFileSystemProviderConfiguration.GIT_ENV_KEY_INIT
			}
		});

		assertThat(fs).isNotNull();

		for (int i = 0; i < 5; i++) {
			final OutputStream outStream = provider.newOutputStream(path);
			assertThat(outStream).isNotNull();
			outStream.write(("my cool content" + i).getBytes());
			outStream.close();
		}

		final JGitVersionAttributeViewImpl attrs = provider.getFileAttributeView(path
				JGitVersionAttributeViewImpl.class);

		assertThat(attrs.readAttributes().history().records().size()).isEqualTo(5);

		for (int i = 0; i < 5; i++) {
			final Path oldPath = provider
							+ "@old-versions-test-repo/some/path/myfile.txt"));
			final InputStream stream = provider.newInputStream(oldPath);
			assertNotNull(stream);
			final String content = new Scanner(stream).useDelimiter("\\A").next();
			assertEquals("my cool content" + i
		}
	}

	@Test
	public void checkProperSquash() throws IOException

		final JGitFileSystem fs = (JGitFileSystem) provider.newFileSystem(newRepo


		final OutputStream aStream = provider.newOutputStream(path);
		aStream.write("my cool content".getBytes());
		aStream.close();
		final RevCommit commit = ((GitImpl) fs.getGit())._log().add(fs.getGit().getRef("master").getObjectId())
				.setMaxCount(1).call().iterator().next();

		final OutputStream bStream = provider.newOutputStream(path2);
		bStream.write("my cool content".getBytes());
		bStream.close();
		final OutputStream cStream = provider.newOutputStream(path3);
		cStream.write("my cool content".getBytes());
		cStream.close();

		final VersionRecord record = makeVersionRecord("aparedes"
				commit.getName());
		final SquashOption squashOption = new SquashOption(record);

		provider.setAttribute(generalPath

		int commitsCount = 0;
		for (RevCommit com : ((GitImpl) fs.getGit())._log().all().call()) {
			commitsCount++;
			System.out.println(com.getName() + " - " + com.getFullMessage());
		}
		assertThat(commitsCount).isEqualTo(2);
	}

	@Test(expected = GitException.class)
	public void testSquashFailBecauseCommitIsFromAnotherBranch() throws IOException

		final JGitFileSystem fs = (JGitFileSystem) provider.newFileSystem(newRepo


		final OutputStream aStream = provider.newOutputStream(path);
		aStream.write("my cool content".getBytes());
		aStream.close();

		final List<RevCommit> commits = getCommitsFromBranch((GitImpl) fs.getGit()

		final OutputStream bStream = provider.newOutputStream(path2);
		bStream.write("my cool content".getBytes());
		bStream.close();
		final OutputStream cStream = provider.newOutputStream(path3);
		cStream.write("my cool content".getBytes());
		cStream.close();

		final VersionRecord record = makeVersionRecord("aparedes"
				commits.get(0).getName());
		final SquashOption squashOption = new SquashOption(record);

		provider.setAttribute(generalPath
	}

	@Test
	public void checkBatchError() throws Exception {

		final FileSystem fs = provider.newFileSystem(newRepo
			{
				put(JGitFileSystemProviderConfiguration.GIT_ENV_KEY_INIT
			}
		});

		provider = spy(provider);

		doThrow(new RuntimeException()).when(provider).notifyDiffs(any(JGitFileSystemImpl.class)
				any(String.class)

		assertThat(fs).isNotNull();

		provider.setAttribute(path
		final OutputStream outStream = provider.newOutputStream(path);
		assertThat(outStream).isNotNull();
		outStream.write(("my cool content").getBytes());
		outStream.close();

		try {
			provider.setAttribute(path
		} catch (Exception ex) {
			fail("Batch can't fail!"
		}
	}

	@Test
	public void resolveFSName() {

		String fsName = "dora-repo";
		assertEquals(fsName
		assertEquals(fsName

		assertEquals(fsName
		assertEquals(fsName

		fsName = "dora-repo/subdir";
		assertEquals(fsName
		assertEquals("dora-repo/subdir"

		assertEquals("dora-repo/subdir"
		assertEquals("dora-repo/subdir"

		fsName = "dora-repo/subdir/subdir";
		assertEquals(fsName
		assertEquals(fsName

		assertEquals(fsName
		assertEquals(fsName
	}

	@Test
	public void resolveSimpleFSNames() throws IOException {


		try {
			fail("should triggered FileSystemNotFoundException");
		} catch (FileSystemNotFoundException e) {
		}

		final FileSystem fs = provider.newFileSystem(newRepo

		assertThat(fs).isNotNull();


		assertEquals(fs
		assertEquals(path.getFileSystem()
	}

	@Test
	public void resolveComposedFSNames() throws IOException {


		final FileSystem fsSimpleName = provider.newFileSystem(simpleName

		assertThat(fsSimpleName).isNotNull();


		final FileSystem fsComposedName = provider.newFileSystem(composedName

		assertThat(fsComposedName).isNotNull();

		assertNotSame(fsSimpleName

		assertEquals(fsSimpleName

		assertEquals(fsComposedName


		assertEquals(fsSimpleName


		assertEquals(fsComposedName
	}

	@Test
	public void validFSNameTest() throws IOException {




	}

	private void checkAmbiguousFS(String fsOriginalName
		provider.newFileSystem(URI.create(fsOriginalName)
		try {
			for (String fsName : ambiguousFsName) {
				provider.newFileSystem(URI.create(fsName)
			}
			fail("ambiguous fs");
		} catch (AmbiguousFileSystemNameException e) {
		}
	}

	@Test
	public void checkRootPath() throws IOException {


		FileSystem fsComposedName = provider.newFileSystem(composedName


		assertEquals(fsComposedName

		assertEquals(fsComposedName
	}

	@Test
	public void getPathForComposedFSNames() throws IOException {


		FileSystem fsComposedName = provider.newFileSystem(composedName

		Path path = provider.getPath(simpleFileName);

		assertEquals(fsComposedName
		assertEquals("/file.txt"


		FileSystem fsSimpleName = provider.newFileSystem(simpleName


		path = provider.getPath(composedFileName);

		assertEquals(fsSimpleName
		assertEquals("/subdir1/file.txt"


		path = provider.getPath(composedFileName);

		assertEquals(fsSimpleName
		assertEquals("/subdir1/subdir2/file.txt"


		path = provider.getPath(composedFileName);

		assertEquals(fsSimpleName
		assertEquals("/subdir1/subdir2/subdir3"
	}

	@Test
	public void getPathForComposedFSNames2() throws IOException {

		FileSystem fsComposedName1 = provider.newFileSystem(composedName


		FileSystem fsComposedName2 = provider.newFileSystem(composedName2


		Path path1 = provider.getPath(composedFileName1);


		Path path2 = provider.getPath(composedFileName2);

		assertNotEquals(fsComposedName1
		assertNotEquals(path1.getFileSystem()

		assertEquals(path2.toString()
	}

	@Test
	public void extractPathTest() throws IOException {


		FileSystem fsComposedName1 = provider.newFileSystem(composedName


		Path path1 = provider.getPath(composedFileName1);

		assertEquals(path1.toString()
	}

	@Test
	public void resolveByRepositoryTest() throws IOException {

				EMPTY_ENV)).getRealJGitFileSystem();

		JGitFileSystemProvider.RepositoryResolverImpl<Object> objectRepositoryResolver = provider.new RepositoryResolverImpl<>();

		assertEquals(fsSimpleName

				EMPTY_ENV)).getRealJGitFileSystem();

		assertEquals(fsComposedName1
				objectRepositoryResolver.resolveFileSystem(fsComposedName1.getGit().getRepository()));
	}

	@Test
	public void extractFSHooksTest() {
		Map<String

		Object hook = (FileSystemHooks.FileSystemHook) context -> {
		};

		env.put("dora"
		env.put(FileSystemHooks.ExternalUpdate.name()

		Map<FileSystemHooks

		assertEquals(1
		assertTrue(fileSystemHooksMap.keySet().contains(FileSystemHooks.ExternalUpdate));
		assertEquals(hook
	}

	@Test
	public void extractCheckBranchAccessHookTest() {
		Map<String

		Object hook = (FileSystemHooks.FileSystemHook) context -> {
		};

		env.put("dora"
		env.put(FileSystemHooks.BranchAccessCheck.name()

		Map<FileSystemHooks

		assertEquals(1
		assertTrue(fileSystemHooksMap.keySet().contains(FileSystemHooks.BranchAccessCheck));
		assertEquals(hook
	}

	@Test
	public void testCloseFileSystem() throws IOException {

		JGitFileSystemProvider fsProvider = spy(new JGitFileSystemProvider(getGitPreferences()) {

			@Override
			protected void setupFileSystemsManager() {
				fsManager = mock(JGitFileSystemsManager.class);
				when(fsManager.allTheFSAreClosed()).thenReturn(true);
			}
		});

		fsProvider.onCloseFileSystem(mock(JGitFileSystem.class));

		verify(fsProvider
	}

	@Test
	public void moveBranchesTest() throws IOException {
		provider.newFileSystem(newRepo

		{

			final OutputStream outStream = provider.newOutputStream(path);
			outStream.write("little baby another-branch".getBytes());
			outStream.close();
		}


		provider.move(source

		Throwable extractContentCall = catchThrowable(

		assertThat(extractContentCall).isInstanceOf(NoSuchFileException.class);

		final String contentMoved = extractContent(

		assertThat(contentMoved).isNotNull().isEqualTo("little baby another-branch");
	}

	@Test
	public void moveBranchesNotAtTheSameFSShouldNotBeAllowedTest() throws IOException {
		provider.newFileSystem(newRepo

		provider.newFileSystem(anotherRepo

		{

			final OutputStream outStream = provider.newOutputStream(path);
			outStream.write("little baby another-branch".getBytes());
			outStream.close();
		}


		assertThatThrownBy(() -> provider.move(source
	}

	@Test
	public void copyBranchesNotAtTheSameFSShouldNotBeAllowedTest() throws IOException {
		provider.newFileSystem(newRepo

		provider.newFileSystem(anotherRepo

		{

			final OutputStream outStream = provider.newOutputStream(path);
			outStream.write("little baby another-branch".getBytes());
			outStream.close();
		}


		assertThatThrownBy(() -> provider.copy(source
	}

	@Test
	public void copyBranchesTest() throws IOException {
		provider.newFileSystem(newRepo

		{

			final OutputStream outStream = provider.newOutputStream(path);
			outStream.write("little baby another-branch".getBytes());
			outStream.close();
		}


		provider.copy(source

		final String originalContent = extractContent(

		assertThat(originalContent).isNotNull().isEqualTo("little baby another-branch");

		final String contentMoved = extractContent(

		assertThat(contentMoved).isNotNull().isEqualTo("little baby another-branch");
	}

	private String extractContent(Path path) throws IOException {
		final InputStream inputStream = provider.newInputStream(path);
		assertThat(inputStream).isNotNull();

		final String content = new Scanner(inputStream).useDelimiter("\\A").next();

		inputStream.close();

		return content;
	}

	private interface MyAttrs extends BasicFileAttributes {

	}

	private VersionRecord makeVersionRecord(final String author
			final Date date
		return new VersionRecord() {
			@Override
			public String id() {
				return commit;
			}

			@Override
			public String author() {
				return author;
			}

			@Override
			public String email() {
				return email;
			}

			@Override
			public String comment() {
				return comment;
			}

			@Override
			public Date date() {
				return date;
			}

			@Override
			public String uri() {
				return null;
			}
		};
	}

	private List<RevCommit> getCommitsFromBranch(final GitImpl origin
			throws GitAPIException
		List<RevCommit> commits = new ArrayList<>();
		final ObjectId id = new GetRef(origin.getRepository()
		for (RevCommit commit : origin._log().add(id).call()) {
			commits.add(commit);
		}
		return commits;
	}
