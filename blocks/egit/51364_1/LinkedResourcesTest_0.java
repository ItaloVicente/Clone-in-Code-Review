			IPath location = linkedFile.getLocation();
			assertNotNull(location);
			assertNotNull(
					RepositoryMapping.getMapping(linkedFile.getProject()));
			assertNull(RepositoryMapping.getMapping(linkedFile));
			assertNull(RepositoryMapping.getMapping(location));
			assertFalse(handler.isEnabled());
			Repository[] repositories = handler.getRepositories();
			assertEquals(handlerClass
					+ " found (unexpected) repository mapping for " + location,
					"[]", Arrays.toString(repositories));
