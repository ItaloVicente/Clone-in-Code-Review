    public void testGetInUnknownPathThrowsPathNotFound() {
        DocumentFragment<Lookup> result = ctx.bucket().lookupIn(key).get("badPath").doLookup();
        try {
            result.content("badPath");
            fail("Getting content by path expected to fail with PathNotFoundException");
        } catch (PathNotFoundException e) {
        }
        try {
            result.content(0);
            fail("Getting content by index expected to fail with PathNotFoundException");
        } catch (PathNotFoundException e) {
        }
