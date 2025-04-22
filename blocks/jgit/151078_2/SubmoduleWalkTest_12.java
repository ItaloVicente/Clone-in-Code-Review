		try (SubmoduleWalk gen = SubmoduleWalk.forIndex(db)) {
			assertTrue(gen.next());
			assertEquals(path
			assertEquals(id
			assertEquals(new File(db.getWorkTree()
			assertNull(gen.getConfigUpdate());
			assertNull(gen.getConfigUrl());
			assertNull(gen.getModulesPath());
			assertNull(gen.getModulesUpdate());
			assertNull(gen.getModulesUrl());
			assertNull(gen.getRepository());
			assertFalse(gen.next());
		}
