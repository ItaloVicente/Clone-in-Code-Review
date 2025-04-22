		try {
			refdir.getRef(name);
			fail("read an invalid reference");
		} catch (IOException err) {
			String msg = err.getMessage();
			assertEquals("Not a ref: " + name + ": " + content, msg);
		}
