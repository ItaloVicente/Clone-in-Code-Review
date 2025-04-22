	@Test
	public void testLongPaths() throws MissingObjectException
			IncorrectObjectTypeException
		TreeFilter longPathFilter = PathFilterGroup
				.createFromStrings(
						"tst/org/eclipse/jgit/treewalk/filter/PathFilterGroupTest.java"
						"tst/org/eclipse/jgit/treewalk/filter/PathFilterGroupTest2.java");
		assertFalse(longPathFilter
				.include(fakeWalk("tst/org/eclipse/jgit/treewalk/FileTreeIteratorTest.java")));
		assertFalse(longPathFilter.include(fakeWalk("tst/a-other-in-same")));
		assertFalse(longPathFilter.include(fakeWalk("a-nothing-in-common")));
	}

