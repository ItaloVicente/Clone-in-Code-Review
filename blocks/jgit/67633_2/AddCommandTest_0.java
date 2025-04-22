    @Test
    public void testAddGitlink() throws Exception {
        createNestedRepo("git-link-dir");
        try (Git git = new Git(db)) {
            git.add().addFilepattern("git-link-dir").call();

            assertEquals(
                    "[git-link-dir
                    indexState(0));
            Set<String> untrackedFiles = git.status().call().getUntracked();
            assert(untrackedFiles.isEmpty());
        }

    }

    @Test
    public void testAddFileInNestedRepo() throws Exception {
        createNestedRepo("git-link-dir");
        try (Git git = new Git(db)) {
            git.add().addFilepattern("git-link-dir/README1.md").call();

            assertEquals(
                    "[git-link-dir/README1.md
                    indexState(0));
            Set<String> untrackedFiles = git.status().call().getUntracked();
            Set<String> addedFiles = git.status().call().getAdded();
            assert(untrackedFiles.contains("git-link-dir/README2.md"));
            assert(addedFiles.contains("git-link-dir/README1.md"));
        }
    }

    @Test
    public void testAddSubrepoWithDirNoGitlinks() throws Exception {
        createNestedRepo("nested-repo");

        StoredConfig config = db.getConfig();
        config.setBoolean(ConfigConstants.CONFIG_CORE_SECTION
                ConfigConstants.CONFIG_KEY_DIRNOGITLINKS
        config.save();

        assert(db.getConfig().get(WorkingTreeOptions.KEY).isDirNoGitLinks());

        try (Git git = new Git(db)) {
            git.add().addFilepattern("nested-repo").call();

            assertEquals(
                    "[nested-repo/README1.md
                    "[nested-repo/README2.md
                    indexState(0));
        }
    }

