		pushTo(provider,
						cloned, provider, //
						"Host localhost", //
						"HostName localhost", //
						"Port " + testPort, //
						"User " + TEST_USER, //
						"IdentityFile " + privateKey.getAbsolutePath()));
