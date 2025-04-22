        AssertionsForClassTypes.assertThat(pathRelative).isNotNull();
        assertThat(pathRelative.getRoot().toString()).isEqualTo("");
        assertThat(pathRelative.toString()).isEqualTo("home");
    }

    @Test
    public void testInputStream() throws IOException {
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
