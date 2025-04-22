    private Logger logger = LoggerFactory.getLogger(JGitFileSystemImplProviderDiffTest.class);

    @Test
    public void testDiffsBetweenBranches() throws IOException {

        final File parentFolder = createTempDirectory();

        final File gitSource = new File(parentFolder,
                                        "repo.git");
        final Git origin = new CreateRepository(gitSource).execute().get();
        final Repository gitRepo = origin.getRepository();

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
                           tempFile("temp1\ntemp1\ntemp3\nmiddle\nmoremiddle\nmoremiddle\nmoremiddle\nother\n"));
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
                           tempFile("temp1\ntemp2\nmiddle\nmoremiddle\nmoremiddle\nmoremiddle\n"));
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


        final Map<String, Object> env = new HashMap<String, Object>() {{
            put(JGitFileSystemProviderConfiguration.GIT_ENV_KEY_DEFAULT_REMOTE_NAME,
                origin.getRepository().getDirectory().toString());
        }};

        provider.newFileSystem(newRepo,
                               env);

        final Path path = provider.getPath(newRepo);
        final BranchDiff branchDiff = (BranchDiff) provider.readAttributes(path,
                                                                           "diff:master,develop").get("diff");

        branchDiff.diffs().forEach(elem -> logger.info(elem.toString()));

        assertThat(branchDiff.diffs().size()).isEqualTo(5);
    }

    @Test
    public void testBranchesDoNotHaveDifferences() throws IOException {

        final File parentFolder = createTempDirectory();

        final File gitSource = new File(parentFolder,
                                        "repo.git");
        final Git origin = new CreateRepository(gitSource).execute().get();
        final Repository gitRepo = origin.getRepository();

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
                           tempFile("temp1\ntemp1\ntemp3\nmiddle\nmoremiddle\nmoremiddle\nmoremiddle\nother\n"));
                   }}).execute();

        new Commit(origin,
                   "master",
                   "name",
                   "name@example.com",
                   "develop-1",
                   null,
                   null,
                   false,
                   new HashMap<String, File>() {{
                       put("file1.txt",
                           tempFile("temp1\ntemp2\nmiddle\nmoremiddle\nmoremiddle\nmoremiddle\n"));
                   }}).execute();

        new CreateBranch((GitImpl) origin,
                         "master",
                         "develop").execute();


        final Map<String, Object> env = new HashMap<String, Object>() {{
            put(JGitFileSystemProviderConfiguration.GIT_ENV_KEY_DEFAULT_REMOTE_NAME,
                origin.getRepository().getDirectory().toString());
        }};

        provider.newFileSystem(newRepo,
                               env);

        final Path path = provider.getPath(newRepo);
        final BranchDiff branchDiff = (BranchDiff) provider.readAttributes(path,
                                                                           "diff:master,develop").get("diff");

        branchDiff.diffs().forEach(elem -> logger.info(elem.toString()));

        assertThat(branchDiff.diffs().size()).isEqualTo(0);
    }
