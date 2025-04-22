        this(hostname, new DefaultServiceRegistry(), ctx, ServiceFactory.INSTANCE, null);
    }

    public CouchbaseNode(final NetworkAddress hostname, final CoreContext ctx, NetworkAddress alternate) {
        this(hostname, new DefaultServiceRegistry(), ctx, ServiceFactory.INSTANCE, alternate);
    }

    CouchbaseNode(final NetworkAddress hostname, ServiceRegistry registry, final CoreContext ctx,
                  ServiceFactory serviceFactory) {
        this(hostname, registry, ctx, serviceFactory, null);
