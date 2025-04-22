    @After
    public void after() {
        if (cluster != null) {
            cluster.disconnect();
        }
    }

