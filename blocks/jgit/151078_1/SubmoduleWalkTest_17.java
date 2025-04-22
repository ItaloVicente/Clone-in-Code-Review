		try (SubmoduleWalk gen = SubmoduleWalk.forIndex(db)) {
			assertTrue(gen.next());
			assertEquals(path
			assertEquals(subId
			assertEquals(new File(db.getWorkTree()
			assertNull(gen.getConfigUpdate());
			assertNull(gen.getConfigUrl());
			assertEquals("sub"
			assertNull(gen.getModulesUpdate());
			assertNull(gen.getRepository());
			assertFalse(gen.next());
		}
