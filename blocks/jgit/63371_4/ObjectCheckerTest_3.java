
		ObjectId id = idFor(OBJ_TREE
		try {
			checker.check(id
			fail("Did not throw CorruptObjectException");
		} catch (CorruptObjectException e) {
			assertSame(TREE_NOT_SORTED
			assertEquals("treeNotSorted: object " + id.name()
					+ ": incorrectly sorted"
		}

