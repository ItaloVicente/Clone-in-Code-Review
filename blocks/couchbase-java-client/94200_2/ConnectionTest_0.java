
    @Test(expected = AuthenticationException.class)
    public void shouldNotAcceptCertAuthWithoutCertAuthEnabled() {
        Cluster cluster = null;
        try {
            cluster = CouchbaseCluster.create(TestProperties.seedNode());
            cluster.authenticate(CertAuthenticator.INSTANCE);
        } finally {
            if (cluster != null) {
                cluster.disconnect();
            }
        }
    }

    @Test(expected = AuthenticationException.class)
    public void shouldNotAcceptCertAuthWithoutKeystoreOrTruststore() {
        CouchbaseEnvironment env = DefaultCouchbaseEnvironment.builder()
            .sslEnabled(true)
            .certAuthEnabled(true)
            .build();
        Cluster cluster = null;
        try {
            cluster = CouchbaseCluster.create(env, TestProperties.seedNode());
            cluster.authenticate(CertAuthenticator.INSTANCE);
        } finally {
            if (cluster != null) {
                cluster.disconnect();
            }
            env.shutdown();
        }
    }

    @Test(expected = AuthenticationException.class)
    public void shouldNotAcceptOtherAuthenticatorIfCertEnabled() {
        CouchbaseEnvironment env = DefaultCouchbaseEnvironment.builder()
            .sslEnabled(true)
            .certAuthEnabled(true)
            .build();
        Cluster cluster = null;
        try {
            cluster = CouchbaseCluster.create(env, TestProperties.seedNode());
            cluster.authenticate("foo", "bar");
        } finally {
            if (cluster != null) {
                cluster.disconnect();
            }
            env.shutdown();
        }
    }

    @Test(expected = AuthenticationException.class)
    public void shouldNotAcceptCertMixedAuth() {
        CouchbaseEnvironment env = DefaultCouchbaseEnvironment.builder()
            .sslEnabled(true)
            .certAuthEnabled(true)
            .sslTruststoreFile("some/file/path")
            .build();
        Cluster cluster = null;
        try {
            cluster = CouchbaseCluster.create(env, TestProperties.seedNode());
            cluster.authenticate(CertAuthenticator.INSTANCE);
            cluster.openBucket(TestProperties.bucket(), "password");
        } finally {
            if (cluster != null) {
                cluster.disconnect();
            }
            env.shutdown();
        }
    }

    @Test(expected = AuthenticationException.class)
    public void shouldNotOpenBucketIfCertNotSetProperly() {
        CouchbaseEnvironment env = DefaultCouchbaseEnvironment.builder()
            .sslEnabled(true)
            .certAuthEnabled(true)
            .build();
        Cluster cluster = null;
        try {
            cluster = CouchbaseCluster.create(env, TestProperties.seedNode());
            cluster.openBucket(TestProperties.bucket());
        } finally {
            if (cluster != null) {
                cluster.disconnect();
            }
            env.shutdown();
        }
    }
