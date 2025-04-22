	@Test
	public void testConfigurableStreamFileThreshold() throws Exception {
		byte[] data = getRng().nextBytes(300);
		RevBlob id = tr.blob(data);
		tr.branch("master").commit().add("A"
		tr.packAndPrune();
		assertTrue("has blob"

		ObjectLoader ol = wc.open(id);
		ObjectStream in = ol.openStream();
		assertTrue(in instanceof ObjectStream.SmallStream);
		assertEquals(300
		in.close();

		wc.setStreamFileThreshold(299);
		ol = wc.open(id);
		in = ol.openStream();
		assertTrue(in instanceof ObjectStream.Filter);
		assertEquals(1
	}

