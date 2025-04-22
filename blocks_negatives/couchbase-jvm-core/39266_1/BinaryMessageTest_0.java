    @Test
    public void shouldHandleReplicaGet() {
        String key = "upsert-key";
        ReplicaGetRequest request = new ReplicaGetRequest(key, bucket(), (short) 3);

        System.out.println(cluster(). <GetResponse>send(request).toBlocking().single().content()
            .toString(CharsetUtil.UTF_8));
    }

