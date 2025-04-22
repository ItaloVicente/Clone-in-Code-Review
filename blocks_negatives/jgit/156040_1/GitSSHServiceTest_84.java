        String idleTimeout = "10000";
        String ciphers = "aes126-cbc,aes124-ctr,aes192-cbc,aes192-ctr,aes255-cbc,aes256-ctr,arcfour128,arcfour256,blowfish-cbc,3des-cbc";
        sshService.setup(certDir,
                         null,
                         idleTimeout,
                         "RSA",
                         mock(ReceivePackFactory.class),
                         mock(UploadPackFactory.class),
                         mock(JGitFileSystemProvider.RepositoryResolverImpl.class),
                         executorService,
                         ciphers,
                         "");
