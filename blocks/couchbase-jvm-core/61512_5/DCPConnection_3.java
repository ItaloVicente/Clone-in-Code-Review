    public DCPConnection(final CoreEnvironment env, final ClusterFacade core, final String bucket, final String password) {
        this(env, core, bucket, password, UnicastAutoReleaseSubject.<DCPRequest>create(env.autoreleaseAfter(),
                TimeUnit.MILLISECONDS, env.scheduler()).toSerialized());
    }

    public DCPConnection(final CoreEnvironment env, final ClusterFacade core, final String bucket, final String password,
                         final SerializedSubject<DCPRequest, DCPRequest> subject) {
