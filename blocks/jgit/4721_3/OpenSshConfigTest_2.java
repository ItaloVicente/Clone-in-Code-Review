		userConfig("Host\tfirst\n" +
				"\tHostName\tfirst.tld\n" +
				"\n" +
				"Host second\n" +
				" HostName\tsecond.tld\n" +
				"Host=third\n" +
				"HostName=third.tld\n\n\n" +
				"\t Host = fourth\n\n\n" +
				" \t HostName\t=fourth.tld\n" +
				"Host\t =     last\n" +
				"HostName  \t    last.tld");
		assertNotNull(userConfig.lookup("first"));
		assertEquals("first.tld"
		assertNotNull(userConfig.lookup("second"));
		assertEquals("second.tld"
		assertNotNull(userConfig.lookup("third"));
		assertEquals("third.tld"
		assertNotNull(userConfig.lookup("fourth"));
		assertEquals("fourth.tld"
		assertNotNull(userConfig.lookup("last"));
		assertEquals("last.tld"
