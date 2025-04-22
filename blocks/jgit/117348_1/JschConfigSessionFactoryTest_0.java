	@Test
	public void testAliasCaseDifferenceUpcase() throws Exception {
		tmpConfigFile = createConfig("Host Bitbucket.org"
				"Hostname bitbucket.org"
				"ConnectTimeout 10"
				"Host bitbucket.org"
				"Port 22"
		tmpConfig = new OpenSshConfig(tmpConfigFile.getParentFile()
				tmpConfigFile);
		factory.setConfig(tmpConfig);
		assertEquals("bitbucket.org"
		assertEquals("foo"
		assertEquals(29418
		assertEquals(TimeUnit.SECONDS.toMillis(10)
	}

	@Test
	public void testAliasCaseDifferenceLowcase() throws Exception {
		tmpConfigFile = createConfig("Host Bitbucket.org"
				"Hostname bitbucket.org"
				"ConnectTimeout 10"
				"Host bitbucket.org"
				"Port 22"
		tmpConfig = new OpenSshConfig(tmpConfigFile.getParentFile()
				tmpConfigFile);
		factory.setConfig(tmpConfig);
		assertEquals("bitbucket.org"
		assertEquals("bar"
		assertEquals(22
		assertEquals(TimeUnit.SECONDS.toMillis(5)
	}

	@Test
	public void testAliasCaseDifferenceUpcaseInverted() throws Exception {
		tmpConfigFile = createConfig("Host bitbucket.org"
				"Hostname bitbucket.org"
				"ConnectTimeout 5"
				"Host Bitbucket.org"
				"Port 29418"
		tmpConfig = new OpenSshConfig(tmpConfigFile.getParentFile()
				tmpConfigFile);
		factory.setConfig(tmpConfig);
		assertEquals("bitbucket.org"
		assertEquals("foo"
		assertEquals(29418
		assertEquals(TimeUnit.SECONDS.toMillis(10)
	}

	@Test
	public void testAliasCaseDifferenceLowcaseInverted() throws Exception {
		tmpConfigFile = createConfig("Host bitbucket.org"
				"Hostname bitbucket.org"
				"ConnectTimeout 5"
				"Host Bitbucket.org"
				"Port 29418"
		tmpConfig = new OpenSshConfig(tmpConfigFile.getParentFile()
				tmpConfigFile);
		factory.setConfig(tmpConfig);
		assertEquals("bitbucket.org"
		assertEquals("bar"
		assertEquals(22
		assertEquals(TimeUnit.SECONDS.toMillis(5)
	}

