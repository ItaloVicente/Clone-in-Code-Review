	public void testAsyncGetBulkWithTranscoderIterator() throws Exception {
		ArrayList<String> keys = new ArrayList<String>();
		keys.add("test1");
		keys.add("test2");
		keys.add("test3");

		ArrayList<Transcoder<String>> tcs = new ArrayList<Transcoder<String>>(keys.size());
		for (String key : keys) {
			tcs.add(new TestWithKeyTranscoder(key));
		}

		for (String key : keys) {
			tcs.add(new TestWithKeyTranscoder(key));
		}

		assertEquals(0, client.asyncGetBulk(keys, tcs.listIterator()).get().size());

		client.set(keys.get(0), 5, "val1", tcs.get(0));
		client.set(keys.get(1), 5, "val2", tcs.get(1));
		Future<Map<String, String>> vals=client.asyncGetBulk(keys, tcs.listIterator());
		assertEquals(2, vals.get().size());
		assertEquals("val1", vals.get().get(keys.get(0)));
		assertEquals("val2", vals.get().get(keys.get(1)));

		keys.add(0, "test4");
		Transcoder<String> encodeTranscoder = new TestWithKeyTranscoder(keys.get(0));
		client.set(keys.get(0), 5, "val4", encodeTranscoder).get();

		Transcoder<String> decodeTranscoder = new TestWithKeyTranscoder("not " + keys.get(0));
		tcs.add(0, decodeTranscoder);
		try {
			client.asyncGetBulk(keys, tcs.listIterator()).get();
			fail("Expected ExecutionException caused by key mismatch");
		} catch (java.util.concurrent.ExecutionException e) {
		}
	}

