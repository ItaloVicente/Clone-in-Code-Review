		assertHost("bad.tld"
	}

	@Test
	public void testAdvancedParsing() throws Exception {
		config("Host foo\n"
				+ " HostName=\"foo\\\"d.tld\"\n"
				+ " User= someone#foo\n"
				+ "Host bar\n"
				+ " User ' some one#two' # Comment\n"
				+ " GlobalKnownHostsFile '/a folder/with spaces/hosts' '/other/more hosts' # Comment\n"
				+ "Host foobar\n"
				+ " User a\\ u\\ thor\n"
				+ "Host backslash\n"
				+ " User some\\one\\\\\\ foo\n");
		assertHost("foo\"d.tld"
		assertUser("someone#foo"
		HostConfig c = lookup("bar");
		assertUser(" some one#two"
		assertArrayEquals(
				new Object[] { "/a folder/with spaces/hosts"
						"/other/more hosts" }
				c.getValues("GlobalKnownHostsFile").toArray());
		assertUser("a u thor"
		assertUser("some\\one\\\\ foo"
