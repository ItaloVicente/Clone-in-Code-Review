		config("Host \"good\"\n" +
			" HostName=\"good.tld\"\n" +
			" Port=\"6007\"\n" +
			" User=\"gooduser\"\n" +
			"Host multiple unquoted and \"quoted\" \"hosts\"\n" +
			" Port=\"2222\"\n" +
			"Host \"spaced\"\n" +
			"# Bad host name, but testing preservation of spaces\n" +
			" HostName=\" spaced\ttld \"\n" +
			"# Misbalanced quotes\n" +
			"Host \"bad\"\n" +
			"# OpenSSH doesn't allow this but ...\n" +
			" HostName=bad.tld\"\n");
		assertEquals("good.tld", osc.lookup("good").getHostName());
		assertEquals("gooduser", osc.lookup("good").getUser());
		assertEquals(6007, osc.lookup("good").getPort());
		assertEquals(2222, osc.lookup("multiple").getPort());
		assertEquals(2222, osc.lookup("quoted").getPort());
		assertEquals(2222, osc.lookup("and").getPort());
		assertEquals(2222, osc.lookup("unquoted").getPort());
		assertEquals(2222, osc.lookup("hosts").getPort());
		assertEquals(" spaced\ttld ", osc.lookup("spaced").getHostName());
		assertEquals("bad.tld\"", osc.lookup("bad").getHostName());
