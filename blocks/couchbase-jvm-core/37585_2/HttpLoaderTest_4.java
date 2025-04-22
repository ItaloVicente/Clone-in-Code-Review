package com.couchbase.client.core.config.loader;

import com.couchbase.client.core.cluster.Cluster;
import com.couchbase.client.core.env.CouchbaseEnvironment;
import com.couchbase.client.core.env.Environment;
import com.couchbase.client.core.message.CouchbaseResponse;
import com.couchbase.client.core.message.ResponseStatus;
import com.couchbase.client.core.message.config.BucketConfigResponse;
import com.couchbase.client.core.message.config.TerseBucketConfigRequest;
import com.couchbase.client.core.message.config.VerboseBucketConfigRequest;
import org.junit.Test;
import rx.Observable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HttpLoaderTest {

    @Test
    public void shouldUseDirectPortIfNotSSL() {
        Environment environment = new CouchbaseEnvironment();
        Cluster cluster = mock(Cluster.class);

        HttpLoader loader = new HttpLoader(cluster, environment);
        assertEquals(environment.bootstrapHttpDirectPort(), loader.port(environment));
    }

    @Test
    public void shouldUseEncryptedPortIfSSL() {
        Environment environment = mock(Environment.class);
        when(environment.sslEnabled()).thenReturn(true);
        when(environment.bootstrapHttpSslPort()).thenReturn(12345);
        Cluster cluster = mock(Cluster.class);

        HttpLoader loader = new HttpLoader(cluster, environment);
        assertEquals(environment.bootstrapHttpSslPort(), loader.port(environment));
    }

    @Test
    public void shouldDiscoverConfigFromTerse() {
        Environment environment = new CouchbaseEnvironment();
        Cluster cluster = mock(Cluster.class);
        Observable<CouchbaseResponse> response = Observable.from(
            (CouchbaseResponse) new BucketConfigResponse("myconfig", ResponseStatus.SUCCESS)
        );
        when(cluster.send(isA(TerseBucketConfigRequest.class))).thenReturn(response);

        HttpLoader loader = new HttpLoader(cluster, environment);
        Observable<String> configObservable = loader.discoverConfig("bucket", "password", "localhost");
        assertEquals("myconfig", configObservable.toBlockingObservable().single());
    }

    @Test
    public void shouldDiscoverConfigFromVerboseAsFallback() {
        Environment environment = new CouchbaseEnvironment();
        Cluster cluster = mock(Cluster.class);
        Observable<CouchbaseResponse> terseResponse = Observable.from(
                (CouchbaseResponse) new BucketConfigResponse(null, ResponseStatus.FAILURE)
        );
        Observable<CouchbaseResponse> verboseResponse = Observable.from(
                (CouchbaseResponse) new BucketConfigResponse("verboseConfig", ResponseStatus.SUCCESS)
        );
        when(cluster.send(isA(TerseBucketConfigRequest.class))).thenReturn(terseResponse);
        when(cluster.send(isA(VerboseBucketConfigRequest.class))).thenReturn(verboseResponse);

        HttpLoader loader = new HttpLoader(cluster, environment);
        Observable<String> configObservable = loader.discoverConfig("bucket", "password", "localhost");
        assertEquals("verboseConfig", configObservable.toBlockingObservable().single());
    }

    @Test
    public void shouldThrowExceptionIfTerseAndVerboseCouldNotBeDiscovered() {
        Environment environment = new CouchbaseEnvironment();
        Cluster cluster = mock(Cluster.class);
        Observable<CouchbaseResponse> terseResponse = Observable.from(
                (CouchbaseResponse) new BucketConfigResponse(null, ResponseStatus.FAILURE)
        );
        Observable<CouchbaseResponse> verboseResponse = Observable.from(
                (CouchbaseResponse) new BucketConfigResponse(null, ResponseStatus.FAILURE)
        );
        when(cluster.send(isA(TerseBucketConfigRequest.class))).thenReturn(terseResponse);
        when(cluster.send(isA(VerboseBucketConfigRequest.class))).thenReturn(verboseResponse);

        HttpLoader loader = new HttpLoader(cluster, environment);
        Observable<String> configObservable = loader.discoverConfig("bucket", "password", "localhost");
        try {
            configObservable.toBlockingObservable().single();
            assertTrue(false);
        } catch(IllegalStateException ex) {
            assertEquals("Bucket config response did not return with success.", ex.getMessage());
        } catch(Exception ex) {
            assertTrue(false);
        }
    }

}
