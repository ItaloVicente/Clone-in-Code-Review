    }*/

    public void shouldQueryExistingView() {
        List<ViewQueryResponse> responses = cluster
            .<ViewQueryResponse>send(new ViewQueryRequest("foo", "bar", false, bucket, password))
            .toList()
            .toBlockingObservable()
            .single();

        System.out.println(responses);
    }*/
