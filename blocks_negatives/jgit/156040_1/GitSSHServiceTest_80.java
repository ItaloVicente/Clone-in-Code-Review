        String idleTimeout = "10000";
        sshService.setup(certDir,
                         null,
                         idleTimeout,
                         "RSA",
                         mock(ReceivePackFactory.class),
                         mock(UploadPackFactory.class),
                         mock(JGitFileSystemProvider.RepositoryResolverImpl.class),
                         executorService,
                         "",
                         "");
