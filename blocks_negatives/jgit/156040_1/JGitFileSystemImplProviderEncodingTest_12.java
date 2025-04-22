    private int gitDaemonPort;

    @Override
    public Map<String, String> getGitPreferences() {
        Map<String, String> gitPrefs = super.getGitPreferences();
        gitPrefs.put(GIT_DAEMON_ENABLED,
                     "true");
        gitDaemonPort = findFreePort();
        gitPrefs.put(GIT_DAEMON_PORT,
                     String.valueOf(gitDaemonPort));
        return gitPrefs;
    }

    @Test
    public void test() throws IOException {

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
                       put("file-name.txt",
                           tempFile("temp1"));
                   }}).execute();

        new Commit(origin.getGit(),
                   "master",
                   "user1",
                   "user1@example.com",
                   "commitx",
                   null,
                   null,
                   false,
                   new HashMap<String, File>() {{
                       put("file+name.txt",
                           tempFile("temp2"));
                   }}).execute();

        new Commit(origin.getGit(),
                   "master",
                   "user1",
                   "user1@example.com",
                   "commitx",
                   null,
                   null,
                   false,
                   new HashMap<String, File>() {{
                       put("file name.txt",
                           tempFile("temp3"));
                   }}).execute();


        final Map<String, Object> env = new HashMap<String, Object>() {{
            put(JGitFileSystemProviderConfiguration.GIT_ENV_KEY_DEFAULT_REMOTE_NAME,
        }};

        final FileSystem fs = provider.newFileSystem(newRepo,
                                                     env);

        assertThat(fs).isNotNull();

        fs.getPath("file+name.txt").toUri();

        provider.getPath(fs.getPath("file+name.txt").toUri());

        URI uri = fs.getPath("file+name.txt").toUri();
        Path path = provider.getPath(uri);
        Path path1 = fs.getPath("file+name.txt");
        assertThat(path).isEqualTo(path1);

        assertThat(provider.getPath(fs.getPath("file name.txt").toUri())).isEqualTo(fs.getPath("file name.txt"));

        assertThat(fs.getPath("file.txt").toUri());

        assertThat(provider.getPath(fs.getPath("file.txt").toUri())).isEqualTo(fs.getPath("file.txt"));
    }
