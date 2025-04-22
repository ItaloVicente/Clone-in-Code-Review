    private static final String SOURCE_GIT = "source/source";

    @Test
    public void testMergeFastForwardSuccessful() throws IOException {
        final File parentFolder = createTempDirectory();

        final File gitSource = new File(parentFolder,
                                        SOURCE_GIT + ".git");
        final Git origin = new CreateRepository(gitSource).execute().get();

        new Commit(origin,
                   "master",
                   "name",
                   "name@example.com",
                   "master-1",
                   null,
                   null,
                   false,
                   new HashMap<String, File>() {{
                       put("file1.txt",
                           tempFile("temp1"));
                   }}).execute();

        new CreateBranch((GitImpl) origin,
                         "master",
                         "develop").execute();

        new Commit(origin,
                   "develop",
                   "name",
                   "name@example.com",
                   "develop-1",
                   null,
                   null,
                   false,
                   new HashMap<String, File>() {{
                       put("file2.txt",
                           tempFile("temp2"));
                   }}).execute();

        new Commit(origin,
                   "develop",
                   "name",
                   "name@example.com",
                   "develop-2",
                   null,
                   null,
                   false,
                   new HashMap<String, File>() {{
                       put("file3.txt",
                           tempFile("temp3"));
                   }}).execute();

        new Commit(origin,
                   "develop",
                   "name",
                   "name@example.com",
                   "develop-3",
                   null,
                   null,
                   false,
                   new HashMap<String, File>() {{
                       put("file4.txt",
                           tempFile("temp4"));
                   }}).execute();

        new Commit(origin,
                   "develop",
                   "name",
                   "name@example.com",
                   "develop-4",
                   null,
                   null,
                   false,
                   new HashMap<String, File>() {{
                       put("file5.txt",
                           tempFile("temp5"));
                   }}).execute();

        new Merge(origin,
                  "develop",
                  "master").execute();

        final List<DiffEntry> result = new ListDiffs(origin,
                                                     new GetTreeFromRef(origin,
                                                                        "master").execute(),
                                                     new GetTreeFromRef(origin,
                                                                        "develop").execute()).execute();

        assertThat(result).isEmpty();
    }

    @Test
    public void testMergeNonFastForwardSuccessful() throws IOException {
        final File parentFolder = createTempDirectory();

        final File gitSource = new File(parentFolder,
                                        SOURCE_GIT + ".git");
        final Git origin = new CreateRepository(gitSource).execute().get();

        new Commit(origin,
                   "master",
                   "name",
                   "name@example.com",
                   "master-1",
                   null,
                   null,
                   false,
                   new HashMap<String, File>() {{
                       put("file1.txt",
                           tempFile("temp1"));
                   }}).execute();

        new CreateBranch((GitImpl) origin,
                         "master",
                         "develop").execute();

        new Commit(origin,
                   "develop",
                   "name",
                   "name@example.com",
                   "develop-1",
                   null,
                   null,
                   false,
                   new HashMap<String, File>() {{
                       put("file2.txt",
                           tempFile("temp2"));
                   }}).execute();

        new Commit(origin,
                   "develop",
                   "name",
                   "name@example.com",
                   "develop-2",
                   null,
                   null,
                   false,
                   new HashMap<String, File>() {{
                       put("file3.txt",
                           tempFile("temp3"));
                   }}).execute();

        new Commit(origin,
                   "develop",
                   "name",
                   "name@example.com",
                   "develop-3",
                   null,
                   null,
                   false,
                   new HashMap<String, File>() {{
                       put("file4.txt",
                           tempFile("temp4"));
                   }}).execute();

        new Commit(origin,
                   "develop",
                   "name",
                   "name@example.com",
                   "develop-4",
                   null,
                   null,
                   false,
                   new HashMap<String, File>() {{
                       put("file5.txt",
                           tempFile("temp5"));
                   }}).execute();

        new Merge(origin,
                  "develop",
                  "master",
                  true).execute();

        final List<DiffEntry> result = new ListDiffs(origin,
                                                     new GetTreeFromRef(origin,
                                                                        "master").execute(),
                                                     new GetTreeFromRef(origin,
                                                                        "develop").execute()).execute();

        assertThat(result).isEmpty();
    }

    @Test
    public void testMergeNoDiff() throws IOException {
        final File parentFolder = createTempDirectory();

        final File gitSource = new File(parentFolder,
                                        SOURCE_GIT + ".git");
        final Git origin = new CreateRepository(gitSource).execute().get();

        new Commit(origin,
                   "master",
                   "name",
                   "name@example.com",
                   "master-1",
                   null,
                   null,
                   false,
                   new HashMap<String, File>() {{
                       put("file1.txt",
                           tempFile("temp1"));
                   }}).execute();

        new CreateBranch((GitImpl) origin,
                         "master",
                         "develop").execute();

        new Commit(origin,
                   "develop",
                   "name",
                   "name@example.com",
                   "develop-1",
                   null,
                   null,
                   false,
                   new HashMap<String, File>() {{
                       put("file1.txt",
                           tempFile("temp1"));
                   }}).execute();

        List<String> commitIds = new Merge(origin,
                                           "develop",
                                           "master").execute();

        assertThat(commitIds).isEmpty();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParametersNotNull() throws IOException {

        new Merge(null,
                  "develop",
                  "master").execute();
    }

    @Test(expected = GitException.class)
    public void testTryToMergeNonexistentBranch() throws IOException {
        final File parentFolder = createTempDirectory();

        final File gitSource = new File(parentFolder,
                                        SOURCE_GIT + ".git");
        final Git origin = new CreateRepository(gitSource).execute().get();

        new Commit(origin,
                   "master",
                   "name",
                   "name@example.com",
                   "master-1",
                   null,
                   null,
                   false,
                   new HashMap<String, File>() {{
                       put("file1.txt",
                           tempFile("temp1"));
                   }}).execute();

        new CreateBranch((GitImpl) origin,
                         "master",
                         "develop").execute();

        new Commit(origin,
                   "develop",
                   "name",
                   "name@example.com",
                   "develop-1",
                   null,
                   null,
                   false,
                   new HashMap<String, File>() {{
                       put("file2.txt",
                           tempFile("temp2"));
                   }}).execute();

        new Commit(origin,
                   "develop",
                   "name",
                   "name@example.com",
                   "develop-2",
                   null,
                   null,
                   false,
                   new HashMap<String, File>() {{
                       put("file3.txt",
                           tempFile("temp3"));
                   }}).execute();

        new Commit(origin,
                   "develop",
                   "name",
                   "name@example.com",
                   "develop-3",
                   null,
                   null,
                   false,
                   new HashMap<String, File>() {{
                       put("file4.txt",
                           tempFile("temp4"));
                   }}).execute();

        new Commit(origin,
                   "develop",
                   "name",
                   "name@example.com",
                   "develop-4",
                   null,
                   null,
                   false,
                   new HashMap<String, File>() {{
                       put("file5.txt",
                           tempFile("temp5"));
                   }}).execute();

        new Merge(origin,
                  "develop",
                  "nonexistent").execute();
    }

    @Test(expected = GitException.class)
    public void testMergeBinaryInformationButHasConflicts() throws IOException {

        final byte[] contentA = this.loadImage("images/drools.png");
        final byte[] contentB = this.loadImage("images/jbpm.png");
        final byte[] contentC = this.loadImage("images/opta.png");

        final File parentFolder = createTempDirectory();

        final File gitSource = new File(parentFolder,
                                        SOURCE_GIT + ".git");
        final Git origin = new CreateRepository(gitSource).execute().get();

        new Commit(origin,
                   "master",
                   "name",
                   "name@example.com",
                   "master-1",
                   null,
                   null,
                   false,
                   new HashMap<String, File>() {{
                       put("file1.jpg",
                           tempFile(contentA));
                   }}).execute();

        new CreateBranch((GitImpl) origin,
                         "master",
                         "develop").execute();

        new Commit(origin,
                   "develop",
                   "name",
                   "name@example.com",
                   "develop-1",
                   null,
                   null,
                   false,
                   new HashMap<String, File>() {{
                       put("file1.jpg",
                           tempFile(contentB));
                   }}).execute();

        new Commit(origin,
                   "master",
                   "name",
                   "name@example.com",
                   "master-1",
                   null,
                   null,
                   false,
                   new HashMap<String, File>() {{
                       put("file1.jpg",
                           tempFile(contentC));
                   }}).execute();

        new Merge(origin,
                  "develop",
                  "master").execute();

        final List<DiffEntry> result = new ListDiffs(origin,
                                                     new GetTreeFromRef(origin,
                                                                        "master").execute(),
                                                     new GetTreeFromRef(origin,
                                                                        "develop").execute()).execute();

        assertThat(result).isEmpty();
    }

    @Test
    public void testMergeBinaryInformationSuccessful() throws IOException {

        final byte[] contentA = this.loadImage("images/drools.png");
        final byte[] contentB = this.loadImage("images/jbpm.png");

        final File parentFolder = createTempDirectory();

        final File gitSource = new File(parentFolder,
                                        SOURCE_GIT + ".git");
        final Git origin = new CreateRepository(gitSource).execute().get();

        new Commit(origin,
                   "master",
                   "name",
                   "name@example.com",
                   "master-1",
                   null,
                   null,
                   false,
                   new HashMap<String, File>() {{
                       put("file1.jpg",
                           tempFile(contentA));
                   }}).execute();

        new CreateBranch((GitImpl) origin,
                         "master",
                         "develop").execute();

        new Commit(origin,
                   "develop",
                   "name",
                   "name@example.com",
                   "develop-1",
                   null,
                   null,
                   false,
                   new HashMap<String, File>() {{
                       put("file1.jpg",
                           tempFile(contentB));
                   }}).execute();

        new Merge(origin,
                  "develop",
                  "master").execute();

        final List<DiffEntry> result = new ListDiffs(origin,
                                                     new GetTreeFromRef(origin,
                                                                        "master").execute(),
                                                     new GetTreeFromRef(origin,
                                                                        "develop").execute()).execute();

        assertThat(result).isEmpty();
    }
