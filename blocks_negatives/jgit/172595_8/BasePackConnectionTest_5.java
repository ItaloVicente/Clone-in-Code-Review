	@Test
	public void testExtractSymRefsFromCapabilities() {
		final Map<String, String> symRefs = BasePackConnection
				.extractSymRefsFromCapabilities(
						Arrays.asList("symref=HEAD:refs/heads/main",
								"symref=refs/heads/sym:refs/heads/other"));

		assertEquals(2, symRefs.size());
		assertEquals("refs/heads/main", symRefs.get("HEAD"));
		assertEquals("refs/heads/other", symRefs.get("refs/heads/sym"));
	}

