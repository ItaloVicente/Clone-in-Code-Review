    public CarrierLoader(Cluster cluster, Environment environment, AtomicReference<List<InetAddress>> seedNodes) {
        super(cluster, environment, seedNodes);
    }

    @Override
    protected ServiceType serviceType() {
        return ServiceType.BINARY;
