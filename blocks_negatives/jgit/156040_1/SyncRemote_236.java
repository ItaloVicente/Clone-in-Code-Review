    void fillBranches(final List<Ref> branches,
                      final Collection<String> remoteBranches,
                      final Collection<String> localBranches) {
        for (final Ref branch : branches) {
            final String branchFullName = branch.getName();
            final String remotePrefix = "refs/remotes/" + remote.getKey() + "/";
            final String localPrefix = "refs/heads/";
