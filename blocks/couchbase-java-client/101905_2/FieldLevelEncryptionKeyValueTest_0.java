        DefaultCouchbaseEnvironment.Builder builder = DefaultCouchbaseEnvironment.builder()
                .cryptoManager(cryptoManager);
        if (CouchbaseTestContext.isMockEnabled()) {
            builder
                .bootstrapCarrierDirectPort(ctx.mock.getCarrierPort(ctx.bucketName()))
                .bootstrapHttpDirectPort(ctx.mock.getHttpPort());
        }
        cluster = CouchbaseCluster.create(builder.build(), TestProperties.seedNode());
