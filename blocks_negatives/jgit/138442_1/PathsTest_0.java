		assertEquals(0, compare(
				a, 0, a.length, FileMode.TREE.getBits(),
				b, 0, b.length, FileMode.GITLINK.getBits()));
		assertEquals(0, compare(
				a, 0, a.length, FileMode.GITLINK.getBits(),
				b, 0, b.length, FileMode.GITLINK.getBits()));
		assertEquals(0, compare(
				a, 0, a.length, FileMode.GITLINK.getBits(),
				b, 0, b.length, FileMode.TREE.getBits()));
