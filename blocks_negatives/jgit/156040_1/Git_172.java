    static Git createRepository(final File repoDir) throws IOException {
        return createRepository(repoDir,
                                null,
                                JGitFileSystemProviderConfiguration.DEFAULT_GIT_HTTP_SSL_VERIFY);
    }

    static Git createRepository(final File repoDir,
                                final boolean sslVerify) throws IOException {
        return createRepository(repoDir,
                                null,
                                sslVerify);
    }

    static Git createRepository(final File repoDir,
                                final File hookDir) throws IOException {
        return createRepository(repoDir,
                                hookDir,
                                null,
                                JGitFileSystemProviderConfiguration.DEFAULT_GIT_HTTP_SSL_VERIFY);
    }

    static Git createRepository(final File repoDir,
                                final File hookDir,
                                final boolean sslVerify) throws IOException {
        return createRepository(repoDir,
                                hookDir,
                                null,
                                sslVerify);
    }

    static Git createRepository(final File repoDir,
                                final File hookDir,
                                final KetchLeaderCache leaders) throws IOException {
        return new CreateRepository(repoDir,
                                    hookDir,
                                    leaders,
                                    JGitFileSystemProviderConfiguration.DEFAULT_GIT_HTTP_SSL_VERIFY).execute().get();
    }

    static Git createRepository(final File repoDir,
                                final File hookDir,
                                final KetchLeaderCache leaders,
                                final boolean sslVerify) {
        try {
            return new CreateRepository(repoDir,
                                        hookDir,
                                        leaders,
                                        sslVerify).execute().get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static Git fork(final File gitRepoContainerDir,
                    final String origin,
                    final String name,
                    final List<String> branches,
                    final CredentialsProvider credential,
                    final KetchLeaderCache leaders,
                    final File hookDir) throws IOException {
        return new Fork(gitRepoContainerDir,
                        origin,
                        name,
                        branches,
                        credential,
                        leaders,
                        hookDir,
                        JGitFileSystemProviderConfiguration.DEFAULT_GIT_HTTP_SSL_VERIFY).execute();
    }

    static Git fork(final File gitRepoContainerDir,
                    final String origin,
                    final String name,
                    final List<String> branches,
                    final CredentialsProvider credential,
                    final KetchLeaderCache leaders,
                    final File hookDir,
                    final boolean sslVerify) throws IOException {
        return new Fork(gitRepoContainerDir,
                        origin,
                        name,
                        branches,
                        credential,
                        leaders,
                        hookDir,
                        sslVerify).execute();
    }

    static Git clone(final File repoDest,
                     final String origin,
                     final boolean isMirror,
                     final List<String> branches,
                     final CredentialsProvider credential,
                     final KetchLeaderCache leaders,
                     final File hookDir) throws IOException {
        return new Clone(repoDest,
                         origin,
                         isMirror,
                         branches,
                         credential,
                         leaders,
                         hookDir,
                         JGitFileSystemProviderConfiguration.DEFAULT_GIT_HTTP_SSL_VERIFY).execute().get();
    }

    static Git clone(final File repoDest,
                     final String origin,
                     final boolean isMirror,
                     final List<String> branches,
                     final CredentialsProvider credential,
                     final KetchLeaderCache leaders,
                     final File hookDir,
                     final boolean sslVerify) throws IOException {
        return new Clone(repoDest,
                         origin,
                         isMirror,
                         branches,
                         credential,
                         leaders,
                         hookDir,
                         sslVerify).execute().get();
    }

    static Git cloneSubdirectory(final File repoDest,
                                 final String origin,
                                 final String subdirectory,
                                 final List<String> branches,
                                 final CredentialsProvider credential,
                                 final KetchLeaderCache leaders,
                                 final File hookDir) throws IOException {
        return new SubdirectoryClone(repoDest,
                                     origin,
                                     subdirectory,
                                     branches,
                                     credential,
                                     leaders,
                                     hookDir,
                                     JGitFileSystemProviderConfiguration.DEFAULT_GIT_HTTP_SSL_VERIFY).execute();
    }

    static Git cloneSubdirectory(final File repoDest,
                                 final String origin,
                                 final String subdirectory,
                                 final List<String> branches,
                                 final CredentialsProvider credential,
                                 final KetchLeaderCache leaders,
                                 final File hookDir,
                                 final boolean sslVerify) throws IOException {
        return new SubdirectoryClone(repoDest,
                                     origin,
                                     subdirectory,
                                     branches,
                                     credential,
                                     leaders,
                                     hookDir,
                                     sslVerify).execute();
    }

    void convertRefTree();

    void deleteRef(final Ref ref) throws IOException;

    Ref getRef(final String ref);

    void push(final CredentialsProvider credentialsProvider,
              final Map.Entry<String, String> remote,
              final boolean force,
              final Collection<RefSpec> refSpecs) throws InvalidRemoteException;

    void gc();

    RevCommit getCommit(final String commitId);

    RevCommit getLastCommit(final String refName);

    RevCommit getLastCommit(final Ref ref) throws IOException;

    RevCommit getCommonAncestorCommit(final String branchA,
                                      final String branchB);

    CommitHistory listCommits(final Ref ref,
                              final String path) throws IOException, GitAPIException;

    List<RevCommit> listCommits(final String startCommitId,
                                final String endCommitId);

    List<RevCommit> listCommits(final ObjectId startRange,
                                final ObjectId endRange);
