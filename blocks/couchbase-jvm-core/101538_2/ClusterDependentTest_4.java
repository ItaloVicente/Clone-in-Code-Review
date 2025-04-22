    public static boolean useMock() {
        return useMock;
    }

    public static void configurPortsIfMocked(DefaultCoreEnvironment.Builder builder) {
        if (ClusterDependentTest.useMock()) {
            builder.bootstrapHttpDirectPort(ClusterDependentTest.mock().getHttpPort());
            builder.bootstrapCarrierDirectPort(
                ClusterDependentTest.mock().getCarrierPort(ClusterDependentTest.bucket())
            );
        }
    }

