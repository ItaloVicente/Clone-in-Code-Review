    @Test
    public void testHasContent() throws IOException {
        commit(git, MASTER_BRANCH, "Adding file into master",
               content(TXT_FILES.get(4), multiline(TXT_FILES.get(4), COMMON_TXT_LINES)));

        Map<String, File> contents = git.mapDiffContent(MASTER_BRANCH,
                                                        git.getFirstCommit(git.getRef(MASTER_BRANCH)).getName(),
                                                        git.getLastCommit(git.getRef(MASTER_BRANCH)).getName());

        assertThat(contents).isNotEmpty();
        assertThat(contents).hasSize(1);
    }

    @Test
    public void testHasContents() throws IOException {
        commit(git, MASTER_BRANCH, "Adding files into master",
               content(TXT_FILES.get(3), multiline(TXT_FILES.get(3), COMMON_TXT_LINES)),
               content(TXT_FILES.get(4), multiline(TXT_FILES.get(4), COMMON_TXT_LINES)));

        Map<String, File> contents = git.mapDiffContent(MASTER_BRANCH,
                                                        git.getFirstCommit(git.getRef(MASTER_BRANCH)).getName(),
                                                        git.getLastCommit(git.getRef(MASTER_BRANCH)).getName());

        assertThat(contents).isNotEmpty();
        assertThat(contents).hasSize(2);
    }

    @Test
    public void testHasDeleteContents() throws IOException {
        new Commit(git, MASTER_BRANCH, "name", "name@example.com", "Removing file",
                   null, null, false,
                   new HashMap<String, File>() {{
                       put(TXT_FILES.get(0), null);
                   }}).execute();

        new Commit(git, MASTER_BRANCH, "name", "name@example.com", "Removing file",
                   null, null, false,
                   new HashMap<String, File>() {{
                       put(TXT_FILES.get(1), null);
                   }}).execute();

        Map<String, File> contents = git.mapDiffContent(MASTER_BRANCH,
                                                        git.getFirstCommit(git.getRef(MASTER_BRANCH)).getName(),
                                                        git.getLastCommit(git.getRef(MASTER_BRANCH)).getName());

        assertThat(contents).isNotEmpty();
        assertThat(contents).hasSize(2);
        contents.values().forEach(v -> assertThat(v).isNull());
    }

    @Test
    public void testWithManyCommitsOneFile() throws IOException {
        commit(git, MASTER_BRANCH, "Updating a file",
               content(TXT_FILES.get(0), "update 1"));

        commit(git, MASTER_BRANCH, "Updating a file",
               content(TXT_FILES.get(0), "update 2"));

        commit(git, MASTER_BRANCH, "Updating a file",
               content(TXT_FILES.get(0), "update 3"));

        Map<String, File> contents = git.mapDiffContent(MASTER_BRANCH,
                                                        git.getFirstCommit(git.getRef(MASTER_BRANCH)).getName(),
                                                        git.getLastCommit(git.getRef(MASTER_BRANCH)).getName());

        assertThat(contents).isNotEmpty();
        assertThat(contents).hasSize(1);
    }

    @Test
    public void testWithMiddleCommits() throws IOException {
        commit(git, MASTER_BRANCH, "Updating a file",
               content(TXT_FILES.get(0), "update 1"));

        RevCommit startCommit = git.getLastCommit(MASTER_BRANCH);

        commit(git, MASTER_BRANCH, "Adding files into master",
               content(TXT_FILES.get(3), multiline(TXT_FILES.get(3), COMMON_TXT_LINES)),
               content(TXT_FILES.get(4), multiline(TXT_FILES.get(4), COMMON_TXT_LINES)));

        new Commit(git, MASTER_BRANCH, "name", "name@example.com", "Removing file",
                   null, null, false,
                   new HashMap<String, File>() {{
                       put(TXT_FILES.get(1), null);
                   }}).execute();

        RevCommit endCommit = git.getLastCommit(MASTER_BRANCH);

        commit(git, MASTER_BRANCH, "Updating a file",
               content(TXT_FILES.get(0), "update 3"));

        Map<String, File> contents = git.mapDiffContent(MASTER_BRANCH,
                                                        startCommit.getName(),
                                                        endCommit.getName());

        assertThat(contents).isNotEmpty();
        assertThat(contents).hasSize(3);
    }

    @Test(expected = GitException.class)
    public void testWithWrongBranchName() throws IOException {
        git.mapDiffContent("wrong-branch-name",
                           git.getFirstCommit(git.getRef(MASTER_BRANCH)).getName(),
                           git.getLastCommit(git.getRef(MASTER_BRANCH)).getName());
    }

    @Test(expected = GitException.class)
    public void testWithInvalidCommit() throws IOException {
        git.mapDiffContent(MASTER_BRANCH,
                           "invalid-commit-id",
                           git.getLastCommit(git.getRef(MASTER_BRANCH)).getName());
    }
