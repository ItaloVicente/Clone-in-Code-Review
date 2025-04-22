				TransportHttp.cookiePathMatches("/some/path", "/some/path"));
		Assert.assertTrue(TransportHttp.cookiePathMatches("/some/path",
				"/some/path/child"));
		Assert.assertTrue(TransportHttp.cookiePathMatches("/some/path/",
				"/some/path/child"));
		Assert.assertFalse(TransportHttp.cookiePathMatches("/some/path",
				"/some/pathother"));
