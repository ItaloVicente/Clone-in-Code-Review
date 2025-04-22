        NetworkAddress na = new NetworkAddress("[::1]", false);
        assertEquals("::1", na.address());
        assertEquals("localhost", na.hostname());
        assertEquals("localhost", na.nameOrAddress());
        assertEquals("::1/localhost", na.nameAndAddress());

