		byte[] data = encode(b.toString());

		checker.checkTree(data);

		checker.setSafeForMacOS(true);
		assertCorrupt(
				"invalid name '.gi\u200Ct' contains ignorable Unicode characters"
				OBJ_TREE
		assertSkipListAccepts(OBJ_TREE
