import static org.junit.Assert.*;

public class ClusterManagerTest {

    private static ClusterManager clusterManager;

    @BeforeClass
    public static void setup() {
        clusterManager = CouchbaseCluster
            .create(TestProperties.seedNode())
            .clusterManager(TestProperties.adminName(), TestProperties.adminPassword())
            .toBlocking()
            .single();
    }

    @Before
    public void clearBuckets() {
        clusterManager
            .getBuckets()
            .flatMap(new Func1<ClusterBucketSettings, Observable<?>>() {
                @Override
                public Observable<?> call(ClusterBucketSettings bucketSettings) {
                    return clusterManager.removeBucket(bucketSettings.name());
                }
            }).toBlocking().lastOrDefault(null);
    }
