	@Test
	public void testJumpHostNone() throws Exception {
				"Host server"
				"HostName localhost"
				"Port " + testPort
				"User " + TEST_USER
				"IdentityFile " + privateKey1.getAbsolutePath()
				"ProxyJump none"
				""
				"Host *"
				"ProxyJump " + TEST_USER + "@localhost:1234");
	}

