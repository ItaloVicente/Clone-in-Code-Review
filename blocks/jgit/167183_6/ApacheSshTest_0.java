		TransportException e = assertThrows(TransportException.class
				() -> cloneWith(
								+ "/doesntmatter"
						defaultCloneDir
						"IdentityFile " + privateKey1.getAbsolutePath()));
		assertFalse(e.getMessage().contains("timeout"));
		assertTrue(e.getMessage().contains("65536")
				|| e.getMessage().contains("closed"));
