        assertThat(content).isNotNull().isEqualTo("temp\n.origin\n.content");
    }

    @Test(expected = NoSuchFileException.class)
    public void testInputStream3() throws IOException {

        final File parentFolder = createTempDirectory();
        final File gitFolder = new File(parentFolder,
                                        "mytest.git");

        final Git origin = new CreateRepository(gitFolder).execute().get();

        new Commit(origin,
                   "master",
                   "user",
                   "user@example.com",
                   "commit message",
                   null,
                   null,
                   false,
                   new HashMap<String, File>() {{
                       put("path/to/file/myfile.txt",
                           tempFile("temp\n.origin\n.content"));
                   }}).execute();


        final Map<String, Object> env = new HashMap<String, Object>() {{
            put(JGitFileSystemProviderConfiguration.GIT_ENV_KEY_DEFAULT_REMOTE_NAME,
                origin.getRepository().getDirectory().toString());
        }};

        final FileSystem fs = provider.newFileSystem(newRepo,
                                                     env);

        assertThat(fs).isNotNull();
