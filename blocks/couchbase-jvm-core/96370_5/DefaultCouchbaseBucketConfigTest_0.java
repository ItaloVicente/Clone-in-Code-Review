            assertFalse(addr.sslServices().isEmpty());
            for (int port : addr.services().values()) {
                assertTrue(port > 0);
            }
            for (int port : addr.sslServices().values()) {
                assertTrue(port > 0);
            }
