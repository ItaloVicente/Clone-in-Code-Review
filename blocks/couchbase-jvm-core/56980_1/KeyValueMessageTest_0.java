    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectEmptyKey() {
        cluster().<GetResponse>send(new GetRequest("", bucket())).toBlocking().single();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectNullKey() {
        cluster().<GetResponse>send(new GetRequest(null, bucket())).toBlocking().single();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectTooLongKey() {
        char[] array = new char[251];
        Arrays.fill(array, 'a');
        cluster().<GetResponse>send(new GetRequest(new String(array), bucket())).toBlocking().single();
    }

