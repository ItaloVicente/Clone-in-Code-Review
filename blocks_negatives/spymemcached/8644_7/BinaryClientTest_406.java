	public void testCASAppendSuccess() throws Exception {
		final String key="append.key";
		assertTrue(client.set(key, 5, "test").get());
		CASValue<Object> casv = client.gets(key);
		assertTrue(client.append(casv.getCas(), key, "es").get());
		assertEquals("testes", client.get(key));
	}
