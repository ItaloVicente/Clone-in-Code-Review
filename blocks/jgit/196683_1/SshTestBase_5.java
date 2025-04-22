		});
	}

	@Test
	void testSshModifiedHostKeyWithProviderDeny() throws Exception {
		assertThrows(TransportException.class
			File copiedHosts = new File(knownHosts.getParentFile()
					"copiedKnownHosts");
			assertTrue(knownHosts.renameTo(copiedHosts)
					"Failed to rename known_hosts");
			createKnownHostsFile(knownHosts
			TestCredentialsProvider provider = new TestCredentialsProvider();
			try {
						provider
						"Host localhost"
						"HostName localhost"
						"Port " + testPort
						"User " + TEST_USER
						"StrictHostKeyChecking yes"
						"IdentityFile " + privateKey1.getAbsolutePath());
			} catch (Exception e) {
				assertEquals(1
						"Expected to be told about the modified key");
				assertTrue(provider.getLog().stream()
						.flatMap(l -> l.getItems().stream()).allMatch(
								c -> c instanceof CredentialItem.InformationalMessage)
						"Only messages expected");
				throw e;
			}
		});
