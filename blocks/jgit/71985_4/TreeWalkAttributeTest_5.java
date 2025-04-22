	@Test
	public void testFilter()
			throws IOException
		writeAttributesFile(".gitattributes"
		writeTrashFile("windows.file"
		writeTrashFile("windows.txt"
		writeTrashFile("readme.txt"
		git.add().addFilepattern(".").call();

		beginWalk();
		walk.setFilter(PathFilter.create("windows.txt"));
		ci_walk.setFilter(PathFilter.create("windows.txt"));


		assertEntry(F

		endWalk();
	}

