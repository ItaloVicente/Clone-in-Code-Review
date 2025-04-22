package com.couchbase.client.core.config;

import com.couchbase.client.core.cluster.Cluster;
import com.couchbase.client.core.env.CouchbaseEnvironment;
import com.couchbase.client.core.env.Environment;
import org.junit.Test;
import rx.Observable;

import static org.mockito.Mockito.mock;

public class DefaultConfigurationProviderTest {

    @Test
    public void shouldOpenBucket() {
        Cluster cluster = mock(Cluster.class);
        Environment environment = new CouchbaseEnvironment();
        ConfigurationProvider provider = new DefaultConfigurationProvider(cluster, environment);

        Observable<ClusterConfig> configObservable = provider.openBucket("bucket", "password");
        System.out.println(configObservable.toBlockingObservable().first());
    }

    @Test
    public void shouldEmitNewClusterConfig() {
        Cluster cluster = mock(Cluster.class);
        Environment environment = new CouchbaseEnvironment();
        ConfigurationProvider provider = new DefaultConfigurationProvider(cluster, environment);

    }

    @Test
    public void shouldFailOpeningBucketIfNoConfigLoaded() {

    }

    @Test
    public void shouldCloseBucket() {

    }

    @Test
    public void shouldCloseBuckets() {

    }

    @Test
    public void shouldAcceptProposedConfig() {

    }
}
