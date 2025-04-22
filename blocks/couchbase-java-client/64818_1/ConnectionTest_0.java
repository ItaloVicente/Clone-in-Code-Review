import static org.junit.Assume.assumeTrue;

import com.couchbase.client.java.cluster.ClusterManager;
import com.couchbase.client.java.error.BucketDoesNotExistException;
import com.couchbase.client.java.error.InvalidPasswordException;
import com.couchbase.client.java.util.TestProperties;
import com.couchbase.client.java.util.features.Version;
import org.junit.Test;
