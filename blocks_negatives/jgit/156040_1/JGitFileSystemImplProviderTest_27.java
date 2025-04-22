        assertThat(rootURIs).isEmpty();
    }

    @Test
    public void testNewFileSystemCloneAndRescan() throws IOException {


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
