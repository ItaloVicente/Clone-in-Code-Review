		config("Host\tfirst\n" +
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
		assertNotNull(osc.lookup("first"));
		assertEquals("first.tld", osc.lookup("first").getHostName());
		assertNotNull(osc.lookup("second"));
		assertEquals("second.tld", osc.lookup("second").getHostName());
		assertNotNull(osc.lookup("third"));
		assertEquals("third.tld", osc.lookup("third").getHostName());
		assertNotNull(osc.lookup("fourth"));
		assertEquals("fourth.tld", osc.lookup("fourth").getHostName());
		assertNotNull(osc.lookup("last"));
		assertEquals("last.tld", osc.lookup("last").getHostName());
