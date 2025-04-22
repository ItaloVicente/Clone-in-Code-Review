
        verify(cluster, times(1)).send(isA(BucketConfigRequest.class));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void shouldFallBackToVerboseIfTerseNotFound() {
        ClusterFacade cluster = mock(ClusterFacade.class);
        Observable<CouchbaseResponse> terseResponse = Observable.just(
            (CouchbaseResponse) new BucketConfigResponse(null, ResponseStatus.NOT_EXISTS)
        );
        Observable<CouchbaseResponse> verboseResponse = Observable.just(
            (CouchbaseResponse) new BucketConfigResponse(null, ResponseStatus.FAILURE)
        );
        when(cluster.send(isA(BucketConfigRequest.class))).thenReturn(terseResponse, verboseResponse);

        HttpLoader loader = new HttpLoader(cluster, environment);
        Observable<String> configObservable = loader.discoverConfig("bucket", "bucket", "password", host);
        try {
            configObservable.toBlocking().single();
            fail();
        } catch(IllegalStateException ex) {
            assertEquals("Could not load bucket configuration: FAILURE(null)", ex.getMessage());
        } catch(Exception ex) {
            fail();
        }

        verify(cluster, times(2)).send(isA(BucketConfigRequest.class));
