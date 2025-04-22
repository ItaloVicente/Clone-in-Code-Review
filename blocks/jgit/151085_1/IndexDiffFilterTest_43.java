		try (TreeWalk treeWalk = createTreeWalk(side)) {
			int count = 0;
			while (treeWalk.next())
				count++;
			assertEquals(2
		}
