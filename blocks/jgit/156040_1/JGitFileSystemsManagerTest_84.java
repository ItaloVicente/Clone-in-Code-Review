	@Before
	public void setup() {
		config = mock(JGitFileSystemProviderConfiguration.class);
		git = mock(Git.class);
		when(git.getRepository()).thenReturn(mock(Repository.class));
	}

	@Test
	public void newFSTest() {
		JGitFileSystem fs = mock(JGitFileSystem.class);
		when(fs.getName()).thenReturn("fs");

		JGitFileSystem fs1 = mock(JGitFileSystem.class);
		when(fs1.getName()).thenReturn("fs1");

		manager = createFSManager();

		manager.newFileSystem(() -> new HashMap<>()
				() -> mock(CredentialsProvider.class)

		manager.newFileSystem(() -> new HashMap<>()
				() -> mock(CredentialsProvider.class)

		assertTrue(manager.containsKey("fs"));

		manager.addClosedFileSystems(fs);

		assertTrue(!manager.allTheFSAreClosed());

		manager.clear();

		assertTrue(manager.allTheFSAreClosed());
	}

	@Test
	public void parseFSTest() {
		manager = new JGitFileSystemsManager(mock(JGitFileSystemProvider.class)

		checkParse("a"

		checkParse("/a"

		checkParse("/a/"

		checkParse("a/b/"

		checkParse("/a/b/"

		checkParse("a/b/c"

		checkParse("a/b/c/d"
	}

	@Test
	public void removeFSTest() {
		JGitFileSystem fs = mock(JGitFileSystem.class);
		when(fs.getName()).thenReturn("fs");

		JGitFileSystem fs1 = mock(JGitFileSystem.class);
		when(fs1.getName()).thenReturn("fs1");

		manager = createFSManager();

		manager.newFileSystem(() -> new HashMap<>()
				() -> mock(CredentialsProvider.class)

		manager.newFileSystem(() -> new HashMap<>()
				() -> mock(CredentialsProvider.class)

		assertTrue(manager.containsKey("fs1"));
		assertTrue(manager.containsRoot("fs1"));
		manager.addClosedFileSystems(fs1);
		assertTrue(manager.getClosedFileSystems().contains("fs1"));

		manager.remove("fs1");
		assertFalse(manager.containsKey("fs1"));
		assertFalse(manager.containsRoot("fs1"));
		assertFalse(manager.containsRoot("fs1"));
	}

	private void checkParse(String fsKey
		List<String> actual = manager.parseFSRoots(fsKey);
		assertEquals(actual.size()
		for (String root : expected) {
			if (!actual.contains(root)) {
				throw new RuntimeException();
			}
		}
		manager.clear();
	}

	private JGitFileSystemsManager createFSManager() {
		return new JGitFileSystemsManager(mock(JGitFileSystemProvider.class)
			@Override
			JGitFileSystemLock createLock(Git git) {
				return mock(JGitFileSystemLock.class);
			}
		};
	}
