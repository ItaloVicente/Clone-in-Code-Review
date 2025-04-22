
    private static volatile CoreEnvironment ENV;

    @BeforeClass
    public static void setup () {
        DefaultCoreEnvironment.Builder builder = DefaultCoreEnvironment.builder();
        ClusterDependentTest.configurPortsIfMocked(builder);
        ENV = builder.build();
    }
