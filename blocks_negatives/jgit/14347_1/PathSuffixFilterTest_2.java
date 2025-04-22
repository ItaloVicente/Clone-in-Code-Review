		}

		List<String> expected =  new LinkedList<String>();
		expected.add("a.txt");
		expected.add("sub/b.txt");

		assertEquals(expected, paths);
