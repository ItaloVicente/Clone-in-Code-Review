public class BinaryMessageTest {

    private static final String seedNode = TestProperties.seedNode();
    private static final String bucket = TestProperties.bucket();
    private static final String password = TestProperties.password();

    private static ClusterFacade cluster;


    @BeforeClass
    public static void connect() {
        cluster = new CouchbaseCore();
        cluster.<SeedNodesResponse>send(new SeedNodesRequest(seedNode)).flatMap(
                new Func1<SeedNodesResponse, Observable<OpenBucketResponse>>() {
                    @Override
                    public Observable<OpenBucketResponse> call(SeedNodesResponse response) {
                        return cluster.send(new OpenBucketRequest(bucket, password));
                    }
                }
        ).toBlocking().single();

        cluster.send(new FlushRequest(bucket, password)).toBlocking().single();
    }

    @AfterClass
    public static void disconnect() throws InterruptedException {
        cluster.send(new DisconnectRequest()).toBlocking().first();
    }
