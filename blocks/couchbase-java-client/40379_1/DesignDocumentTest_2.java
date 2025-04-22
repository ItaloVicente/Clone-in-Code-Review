        DesignDocument found = manager.getDesignDocument("remove1").toBlocking().single();
        assertNotNull(found);
        assertEquals("remove1", found.name());
        assertEquals(2, found.views().size());

        assertTrue(manager.removeDesignDocument("remove1").toBlocking().single());

        found = manager.getDesignDocument("remove1").toBlocking().singleOrDefault(null);
        assertNull(found);
