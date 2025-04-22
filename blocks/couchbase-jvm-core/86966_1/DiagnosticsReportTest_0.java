
        assertNotNull(sh.sdk());
        assertEquals(sh.sdk(), env().userAgent());
        assertEquals("diag-id-provided", sh.id());

        assertNotNull(sh.exportToJson());
