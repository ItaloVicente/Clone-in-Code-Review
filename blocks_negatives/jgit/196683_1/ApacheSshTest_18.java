						() -> cloneWith(
						"Host server", //
						"HostName localhost", //
						"Port " + testPort, //
						"User " + TEST_USER, //
						"IdentityFile " + privateKey1.getAbsolutePath(), //
						"ProxyJump " + TEST_USER + "X@proxy", //
						"", //
						"Host proxy", //
						"Hostname localhost", //
						"Port " + proxy1.getPort(), //
						"IdentityFile " + privateKey2.getAbsolutePath(), //
						"", //
						"Host proxy2", //
						"Hostname localhost", //
						"User foo", //
						"ProxyJump " + TEST_USER + "X@proxy", //
						"IdentityFile " + privateKey1.getAbsolutePath()));
