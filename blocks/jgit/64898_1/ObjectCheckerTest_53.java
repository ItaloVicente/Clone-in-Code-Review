		byte[] data = encode(b.toString());

		checker.checkTree(data);

		checker.setSafeForMacOS(true);
		assertCorrupt(
				"invalid name '.git\uFEFF' contains ignorable Unicode characters"
				OBJ_TREE
		assertSkipListAccepts(OBJ_TREE
		checker.setIgnore(HAS_DOTGIT
		checker.checkTree(data);
