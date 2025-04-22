    @Test
    public void shouldFailTouchWhenNotExist() {
        String key = "touchFail";
        JsonDocument touchDoc = JsonDocument.create(key);

        try {
            bucket().touch(key, 3);
            fail("touch(key, expiry) with unknown key expected to fail");
        } catch (DocumentDoesNotExistException e) {
        }

        try {
            bucket().touch(touchDoc);
            fail("touch(document) with unknown key expected to fail");
        } catch (DocumentDoesNotExistException e) {
        }
    }

