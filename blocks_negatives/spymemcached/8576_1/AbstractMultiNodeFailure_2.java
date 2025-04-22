            logger.error(ex);
        }
        try {
                    assertEquals(OBJ_KEY, memcachedClient.get(OBJ_KEY));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            fail("Fail during getting data with primary non active node");
