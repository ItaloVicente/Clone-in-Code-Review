
    /**
     * verify set and get go to the right place
     */
    public void verifySetAndGet() {
	int iterations = 20;
	for (int i = 0; i < iterations; i++) {
	    mc.set("test" + i, 0, "test" + i);
	}

	for (int i = 0; i < iterations; i++) {
	    Object res = mc.get("test" + i);
	    assertEquals("test" + i, res);
	}
