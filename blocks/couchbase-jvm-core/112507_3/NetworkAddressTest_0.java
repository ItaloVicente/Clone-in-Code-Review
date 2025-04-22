
    @Test
    public void shouldCreateFromIpv6Address() {
        NetworkAddress na = new NetworkAddress("[::1]", false);
        assertEquals("::1", na.address());
        assertEquals("0:0:0:0:0:0:0:1", na.hostname());
        assertEquals("0:0:0:0:0:0:0:1", na.nameOrAddress());
        assertEquals("::1/0:0:0:0:0:0:0:1", na.nameAndAddress());

        NetworkAddress na2 = new NetworkAddress("[2001:DB4:BAD:A55::5]", false);
        assertEquals("2001:DB4:BAD:A55::5".toLowerCase(), na2.address());
        assertEquals("2001:DB4:BAD:A55:0:0:0:5".toLowerCase(), na2.hostname());
        assertEquals("2001:DB4:BAD:A55:0:0:0:5".toLowerCase(), na2.nameOrAddress());
        assertEquals("2001:db4:bad:a55::5/2001:db4:bad:a55:0:0:0:5", na2.nameAndAddress());
    }
