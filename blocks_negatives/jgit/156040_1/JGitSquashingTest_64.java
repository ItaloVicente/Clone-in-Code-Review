    private Logger logger = LoggerFactory.getLogger(JGitSquashingTest.class);

    static {
        CredentialsProvider.setDefault(new UsernamePasswordCredentialsProvider("guest",
                                                                               ""));
    }

    /*
     * This test make 5 commits and then squah the last 4 into a single commit
     */
    @Test
    public void testSquash4Of5Commits() throws IOException, GitAPIException {

        final File parentFolder = createTempDirectory();
        logger.info(">> Parent Forlder for the Test: " + parentFolder.getAbsolutePath());
        final File gitFolder = new File(parentFolder,
                                        "my-local-repo.git");

        final Git origin = new CreateRepository(gitFolder).execute().get();

        new Commit(origin,
                   "master",
                   "salaboy",
                   "salaboy@example.com",
                   "commit 1!",
                   null,
                   null,
                   false,
                   new HashMap<String, File>() {{
                       put("path/to/file1.txt",
                           tempFile("initial content file 1"));
                   }}).execute();
        new Commit(origin,
                   "master",
                   "salaboy",
                   "salaboy@example.com",
                   "commit 2!",
                   null,
                   null,
                   false,
                   new HashMap<String, File>() {{
                       put("path/to/file2.txt",
                           tempFile("initial content file 2"));
                   }}).execute();
        Iterable<RevCommit> logs = ((GitImpl) origin)._log().setMaxCount(1).all().call();
        RevCommit secondCommit = logs.iterator().next();

        new Commit(origin,
                   "master",
                   "salaboy",
                   "salaboy@example.com",
                   "commit 3!",
                   null,
                   null,
                   false,
                   new HashMap<String, File>() {{
                       put("path/to/file1.txt",
                           tempFile("new content file 1"));
                   }}).execute();

        new Commit(origin,
                   "master",
                   "salaboy",
                   "salaboy@example.com",
                   "commit 4!",
                   null,
                   null,
                   false,
                   new HashMap<String, File>() {{
                       put("path/to/file2.txt",
                           tempFile("new content file 2"));
                   }}).execute();
        new Commit(origin,
                   "master",
                   "salaboy",
                   "salaboy@example.com",
                   "commit 5!",
                   null,
                   null,
                   false,
                   new HashMap<String, File>() {{
                       put("path/to/file3.txt",
                           tempFile("initial content file 3"));
                   }}).execute();
        logs = ((GitImpl) origin)._log().all().call();
        int commitsCount = 0;
        for (RevCommit commit : logs) {
            logger.info(">>> Origin Commit: " + commit.getFullMessage() + " - " + commit.toString());
            commitsCount++;
        }
        assertThat(commitsCount).isEqualTo(5);

        assertThat(origin.getPathInfo("master",
                                      "pathx/").getPathType()).isEqualTo(NOT_FOUND);
        assertThat(origin.getPathInfo("master",
                                      "path/to/file1.txt").getPathType()).isEqualTo(FILE);
        assertThat(origin.getPathInfo("master",
                                      "path/to/file2.txt").getPathType()).isEqualTo(FILE);
        assertThat(origin.getPathInfo("master",
                                      "path/to/file3.txt").getPathType()).isEqualTo(FILE);
        assertThat(origin.getPathInfo("master",
                                      "path/to").getPathType()).isEqualTo(DIRECTORY);

        logger.info("Squashing from " + secondCommit.getName() + "  to HEAD");
        new Squash((GitImpl) origin,
                   "master",
                   secondCommit.getName(),
                   "squashed message").execute();

        commitsCount = 0;
        for (RevCommit commit : ((GitImpl) origin)._log().all().call()) {
            logger.info(">>> Final Commit: " + commit.getFullMessage() + " - " + commit.toString());
            commitsCount++;
        }
        assertThat(commitsCount).isEqualTo(2);
    }

    @Test
    public void testFailWhenTryToSquashCommitsFromDifferentBranches() throws IOException, GitAPIException {

        final File parentFolder = createTempDirectory();
        logger.info(">> Parent Forlder for the Test: " + parentFolder.getAbsolutePath());
        final File gitFolder = new File(parentFolder,
                                        "my-local-repo.git");

        final Git origin = new CreateRepository(gitFolder).execute().get();

        new Commit(origin,
                   "master",
                   "aparedes",
                   "aparedes@example.com",
                   "commit 1!",
                   null,
                   null,
                   false,
                   new HashMap<String, File>() {{
                       put("path/to/file1.txt",
                           tempFile("initial content file 1"));
                   }}).execute();
        new Commit(origin,
                   "develop",
                   "salaboy",
                   "salaboy@example.com",
                   "commit 2!",
                   null,
                   null,
                   false,
                   new HashMap<String, File>() {{
                       put("path/to/file2.txt",
                           tempFile("initial content file 2"));
                   }}).execute();
        new Commit(origin,
                   "master",
                   "aparedes",
                   "aparedes@example.com",
                   "commit 3!",
                   null,
                   null,
                   false,
                   new HashMap<String, File>() {{
                       put("path/to/file3.txt",
                           tempFile("initial content file 1"));
                   }}).execute();
        new Commit(origin,
                   "master",
                   "aparedes",
                   "aparedes@example.com",
                   "commit 4!",
                   null,
                   null,
                   false,
                   new HashMap<String, File>() {{
                       put("path/to/file4.txt",
                           tempFile("initial content file 1"));
                   }}).execute();

        List<RevCommit> masterCommits = getCommitsFromBranch((GitImpl) origin,
                                                             "master");
        List<RevCommit> developCommits = getCommitsFromBranch((GitImpl) origin,
                                                              "develop");

        assertThat(masterCommits.size()).isEqualTo(3);
        assertThat(developCommits.size()).isEqualTo(1);

        try {
            new Squash((GitImpl) origin,
                       "master",
                       developCommits.get(0).getName(),
                       "squashed message").execute();
            fail("If it reaches here the test has failed because he found the commit into the branch");
        } catch (GitException e) {
            logger.info(e.getMessage());
            assertThat(e).isNotNull();
        }
    }

    private List<RevCommit> getCommitsFromBranch(final GitImpl origin,
                                                 String branch) throws GitAPIException, MissingObjectException, IncorrectObjectTypeException {
        List<RevCommit> commits = new ArrayList<>();
        final ObjectId id = new GetRef(origin.getRepository(),
                                       branch).execute().getObjectId();
        for (RevCommit commit : origin._log().add(id).call()) {
            logger.info(">>> " + branch + " Commits: " + commit.getFullMessage() + " - " + commit.toString());
            commits.add(commit);
        }
        return commits;
    }

    /*
     * This test also perform 5 commits and squash the last 4 into a single commit
     *  but now the changes are in different paths
     */
    @Test
    public void testSquashCommitsWithDifferentPaths() throws IOException, GitAPIException {

        final File parentFolder = createTempDirectory();
        logger.info(">> Parent Folder for the Test: " + parentFolder.getAbsolutePath());
        final File gitFolder = new File(parentFolder,
                                        "my-local-repo.git");

        final Git origin = new CreateRepository(gitFolder).execute().get();

        new Commit(origin,
                   "master",
                   "salaboy",
                   "salaboy@example.com",
                   "commit 1!",
                   null,
                   null,
                   false,
                   new HashMap<String, File>() {{
                       put("file1.txt",
                           tempFile("initial content file 1"));
                   }}).execute();
        new Commit(origin,
                   "master",
                   "salaboy",
                   "salaboy@example.com",
                   "commit 2!",
                   null,
                   null,
                   false,
                   new HashMap<String, File>() {{
                       put("path/to/file2.txt",
                           tempFile("initial content file 2"));
                   }}).execute();
        Iterable<RevCommit> logs = ((GitImpl) origin)._log().setMaxCount(1).all().call();
        RevCommit secondCommit = logs.iterator().next();

        new Commit(origin,
                   "master",
                   "salaboy",
                   "salaboy@example.com",
                   "commit 3!",
                   null,
                   null,
                   false,
                   new HashMap<String, File>() {{
                       put("file1.txt",
                           tempFile("new content file 1"));
                   }}).execute();

        new Commit(origin,
                   "master",
                   "salaboy",
                   "salaboy@example.com",
                   "commit 4!",
                   null,
                   null,
                   false,
                   new HashMap<String, File>() {{
                       put("path/to/file2.txt",
                           tempFile("new content file 2"));
                   }}).execute();
        new Commit(origin,
                   "master",
                   "salaboy",
                   "salaboy@example.com",
                   "commit 5!",
                   null,
                   null,
                   false,
                   new HashMap<String, File>() {{
                       put("path/file3.txt",
                           tempFile("initial content file 3"));
                   }}).execute();

        for (RevCommit commit : ((GitImpl) origin)._log().all().call()) {
            logger.info(">>> Origin Commit: " + commit.getFullMessage() + " - " + commit.toString());
        }

        assertThat(origin.getPathInfo("master",
                                      "pathx/").getPathType()).isEqualTo(NOT_FOUND);
        assertThat(origin.getPathInfo("master",
                                      "file1.txt").getPathType()).isEqualTo(FILE);
        assertThat(origin.getPathInfo("master",
                                      "path/to/file2.txt").getPathType()).isEqualTo(FILE);
        assertThat(origin.getPathInfo("master",
                                      "path/file3.txt").getPathType()).isEqualTo(FILE);
        assertThat(origin.getPathInfo("master",
                                      "path/to").getPathType()).isEqualTo(DIRECTORY);

        logger.info("Squashing from " + secondCommit.getName() + "  to HEAD");
        new Squash((GitImpl) origin,
                   "master",
                   secondCommit.getName(),
                   "squashed message").execute();

        int commitsCount = 0;
        for (RevCommit commit : ((GitImpl) origin)._log().all().call()) {
            logger.info(">>> Final Commit: " + commit.getFullMessage() + " - " + commit.toString());
            commitsCount++;
        }

        assertThat(commitsCount).isEqualTo(2);
    }
