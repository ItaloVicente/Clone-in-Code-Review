    @Test
    public void shouldIncrementFromCounter() {
        String key = "counter-incr";

        CounterResponse response1 = cluster().<CounterResponse>send(new CounterRequest(key, 0, 10, 0, bucket())).toBlocking().single();
        assertEquals(0, response1.value());

        CounterResponse response2 = cluster().<CounterResponse>send(new CounterRequest(key, 0, 10, 0, bucket())).toBlocking().single();
        assertEquals(10, response2.value());

        CounterResponse response3 = cluster().<CounterResponse>send(new CounterRequest(key, 0, 10, 0, bucket())).toBlocking().single();
        assertEquals(20, response3.value());

        assertTrue(response1.cas() != response2.cas());
        assertTrue(response2.cas() != response3.cas());
    }

    @Test
    public void shouldDecrementFromCounter() {
        String key = "counter-decr";

        CounterResponse response1 = cluster().<CounterResponse>send(new CounterRequest(key, 100, -10, 0, bucket())).toBlocking().single();
        assertEquals(100, response1.value());

        CounterResponse response2 = cluster().<CounterResponse>send(new CounterRequest(key, 100, -10, 0, bucket())).toBlocking().single();
        assertEquals(90, response2.value());

        CounterResponse response3 = cluster().<CounterResponse>send(new CounterRequest(key, 100, -10, 0, bucket())).toBlocking().single();
        assertEquals(80, response3.value());

        assertTrue(response1.cas() != response2.cas());
        assertTrue(response2.cas() != response3.cas());
    }

