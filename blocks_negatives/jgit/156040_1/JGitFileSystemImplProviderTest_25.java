    private int gitDaemonPort;

    @Override
    public Map<String, String> getGitPreferences() {
        Map<String, String> gitPrefs = super.getGitPreferences();
        gitPrefs.put(GIT_DAEMON_ENABLED,
                     "true");
        gitDaemonPort = findFreePort();
        gitPrefs.put(GIT_DAEMON_PORT,
                     String.valueOf(gitDaemonPort));
        System.out.println(gitDaemonPort);
        return gitPrefs;
    }

    @Test
    public void testNewFileSystem() throws IOException {

        final FileSystem fs = provider.newFileSystem(newRepo,
                                                     EMPTY_ENV);

        assertThat(fs).isNotNull();

        final DirectoryStream<Path> stream = provider.newDirectoryStream(provider.getPath(newRepo),
                                                                         null);
        assertThat(stream).isNotNull().hasSize(0);

        try {
            provider.newFileSystem(newRepo,
                                   EMPTY_ENV);
            failBecauseExceptionWasNotThrown(FileSystemAlreadyExistsException.class);
        } catch (final Exception ignored) {
        }

                               EMPTY_ENV);
    }

    @Test
    public void testNewFileSystemInited() throws IOException {

        final Map<String, ?> env = new HashMap<String, Object>() {{
            put("init",
                Boolean.TRUE);
        }};

        final FileSystem fs = provider.newFileSystem(newRepo,
                                                     env);

        assertThat(fs).isNotNull();

        final DirectoryStream<Path> stream = provider.newDirectoryStream(provider.getPath(newRepo),
                                                                         null);
        assertThat(stream).isNotNull().hasSize(1);
    }

    @Test
    public void testInvalidURINewFileSystem() throws IOException {

        try {
            provider.newFileSystem(newRepo,
                                   EMPTY_ENV);
            failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
        } catch (final IllegalArgumentException ex) {
            assertThat(ex.getMessage()).isEqualTo("Parameter named 'uri' is invalid, missing host repository!");
        }
    }

    @Test
    public void testNewFileSystemClone() throws IOException {


        final JGitFileSystem origin = (JGitFileSystem) provider.newFileSystem(originRepo,
                                                                              Collections.emptyMap());

        new Commit(origin.getGit(),
                   "master",
                   "user1",
                   "user1@example.com",
                   "commitx",
                   null,
                   null,
                   false,
                   new HashMap<String, File>() {{
                       put("file.txt",
                           tempFile("temp"));
                   }}).execute();


        final Map<String, Object> env = new HashMap<String, Object>() {{
            put(JGitFileSystemProviderConfiguration.GIT_ENV_KEY_DEFAULT_REMOTE_NAME,
        }};

        final FileSystem fs = provider.newFileSystem(newRepo,
                                                     env);

        assertThat(fs).isNotNull();

        assertThat(fs.getRootDirectories()).hasSize(1);

        assertThat(fs.getPath("file.txt").toFile()).isNotNull().exists();

        new Commit(origin.getGit(),
                   "master",
                   "user1",
                   "user1@example.com",
                   "commitx",
                   null,
                   null,
                   false,
                   new HashMap<String, File>() {{
                       put("fileXXXXX.txt",
                           tempFile("temp"));
                   }}).execute();


        assertThat(fs).isNotNull();

        assertThat(fs.getRootDirectories()).hasSize(1);

        for (final Path root : fs.getRootDirectories()) {
            if (root.toAbsolutePath().toUri().toString().contains("upstream")) {
                assertThat(provider.newDirectoryStream(root,
                                                       null)).hasSize(2);
            } else if (root.toAbsolutePath().toUri().toString().contains("origin")) {
                assertThat(provider.newDirectoryStream(root,
                                                       null)).hasSize(1);
            } else {
                assertThat(provider.newDirectoryStream(root,
                                                       null)).hasSize(2);
            }
        }

        new Commit(origin.getGit(),
                   "master",
                   "user1",
                   "user1@example.com",
                   "commitx",
                   null,
                   null,
                   false,
                   new HashMap<String, File>() {{
                       put("fileYYYY.txt",
                           tempFile("tempYYYY"));
                   }}).execute();


        assertThat(fs.getRootDirectories()).hasSize(1);

        assertThat(provider.newDirectoryStream(fs.getRootDirectories().iterator().next(),
                                               null)).isNotEmpty().hasSize(3);
    }

    @Test
    public void testNewFileSystemCloneAndPush() throws IOException {


        final JGitFileSystem origin = (JGitFileSystem) provider.newFileSystem(originRepo,
                                                                              Collections.emptyMap());

        new Commit(origin.getGit(),
                   "master",
                   "user1",
                   "user1@example.com",
                   "commitx",
                   null,
                   null,
                   false,
                   new HashMap<String, File>() {{
                       put("file.txt",
                           tempFile("temp"));
                   }}).execute();


        final Map<String, Object> env = new HashMap<String, Object>() {{
            put(JGitFileSystemProviderConfiguration.GIT_ENV_KEY_DEFAULT_REMOTE_NAME,
        }};

        final FileSystem fs = provider.newFileSystem(newRepo,
                                                     env);

        assertThat(fs).isNotNull();

        assertThat(fs.getRootDirectories()).hasSize(1);

        assertThat(fs.getPath("file.txt").toFile()).isNotNull().exists();

        new Commit(((JGitFileSystem) fs).getGit(),
                   "master",
                   "user1",
                   "user1@example.com",
                   "commitx",
                   null,
                   null,
                   false,
                   new HashMap<String, File>() {{
                       put("fileXXXXX.txt",
                           tempFile("temp"));
                   }}).execute();


        final Map<String, Object> env2 = new HashMap<String, Object>() {{
            put(JGitFileSystemProviderConfiguration.GIT_ENV_KEY_DEFAULT_REMOTE_NAME,
        }};

        final FileSystem fs2 = provider.newFileSystem(newRepo2,
                                                      env2);

        new Commit(origin.getGit(),
                   "user-branch",
                   "user1",
                   "user1@example.com",
                   "commitx",
                   null,
                   null,
                   false,
                   new HashMap<String, File>() {{
                       put("file1UserBranch.txt",
                           tempFile("tempX"));
                   }}).execute();


        assertThat(fs2.getRootDirectories()).hasSize(2);

        final List<String> rootURIs1 = new ArrayList<String>() {{
        }};

        final List<String> rootURIs2 = new ArrayList<String>() {{
        }};
