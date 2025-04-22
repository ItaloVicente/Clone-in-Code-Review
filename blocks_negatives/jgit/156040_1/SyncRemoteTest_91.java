    @Test
    public void fillBranchesTest() {
        final List<Ref> branches = Arrays.asList(createBranch("refs/heads/local/branch1"),
                                                 createBranch("refs/heads/localBranch2"),
                                                 createBranch("refs/remotes/upstream/remote/branch1"),
                                                 createBranch("refs/remotes/upstream/remoteBranch2"));
