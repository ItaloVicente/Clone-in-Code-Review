        new Commit(origin,
                   "master",
                   "user1",
                   "user1@example.com",
                   "commitx",
                   null,
                   null,
                   false,
                   new HashMap<String, File>() {{
                       put("file.txt",
                           tempFile("temp.origin.content.2"));
                   }}).execute();


        final Map<String, Object> env = new HashMap<String, Object>() {{
            put(JGitFileSystemProviderConfiguration.GIT_ENV_KEY_DEFAULT_REMOTE_NAME,
                origin.getRepository().getDirectory().toString());
        }};

        final FileSystem fs = provider.newFileSystem(newRepo,
                                                     env);

        assertThat(fs).isNotNull();


        provider.newInputStream(path);
    }

    @Test
    public void testNewOutputStream() throws Exception {
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
                       put("myfile.txt",
                           tempFile("temp\n.origin\n.content"));
                   }}).execute();
        new Commit(origin,
                   "user_branch",
                   "user",
                   "user@example.com",
                   "commit message",
                   null,
                   null,
                   false,
                   new HashMap<String, File>() {{
                       put("path/to/some/file/myfile.txt",
                           tempFile("some\n.content\nhere"));
                   }}).execute();


        final Map<String, Object> env = new HashMap<String, Object>() {{
            put(JGitFileSystemProviderConfiguration.GIT_ENV_KEY_DEFAULT_REMOTE_NAME,
                origin.getRepository().getDirectory().toString());
        }};

        final FileSystem fs = provider.newFileSystem(newRepo,
                                                     env);

        assertThat(fs).isNotNull();


        final OutputStream outStream = provider.newOutputStream(path);
        assertThat(outStream).isNotNull();
        outStream.write("my cool content".getBytes());
        outStream.close();

        final InputStream inStream = provider.newInputStream(path);

        final String content = new Scanner(inStream).useDelimiter("\\A").next();

        inStream.close();

        assertThat(content).isNotNull().isEqualTo("my cool content");

        try {
            failBecauseExceptionWasNotThrown(IOException.class);
        } catch (Exception ignored) {
        }
    }

    @Test
    public void testNewOutputStreamWithJGitOp() throws Exception {
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
                       put("myfile.txt",
                           tempFile("temp\n.origin\n.content"));
                   }}).execute();
        new Commit(origin,
                   "user_branch",
                   "user",
                   "user@example.com",
                   "commit message",
                   null,
                   null,
                   false,
                   new HashMap<String, File>() {{
                       put("path/to/some/file/myfile.txt",
                           tempFile("some\n.content\nhere"));
                   }}).execute();


        final Map<String, Object> env = new HashMap<String, Object>() {{
            put(JGitFileSystemProviderConfiguration.GIT_ENV_KEY_DEFAULT_REMOTE_NAME,
                origin.getRepository().getDirectory().toString());
        }};

        final FileSystem fs = provider.newFileSystem(newRepo,
                                                     env);

        assertThat(fs).isNotNull();

        final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        final CommentedOption op = new CommentedOption("User Tester",
                                                       "user.tester@example.com",
                                                       "omg, is it the end?",
                                                       formatter.parse("31/12/2012"));


        final OutputStream outStream = provider.newOutputStream(path,
                                                                op);
        assertThat(outStream).isNotNull();
        outStream.write("my cool content".getBytes());
        outStream.close();

        final InputStream inStream = provider.newInputStream(path);

        final String content = new Scanner(inStream).useDelimiter("\\A").next();
