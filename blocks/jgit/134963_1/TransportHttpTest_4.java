				TransportHttp.matchesCookiePath("/some/path"
		Assert.assertTrue(TransportHttp.matchesCookiePath("/some/path/child"
				"/some/path"));
		Assert.assertTrue(TransportHttp.matchesCookiePath("/some/path/child"
				"/some/path/"));
		Assert.assertFalse(TransportHttp.matchesCookiePath("/some/pathother"
				"/some/path"));
