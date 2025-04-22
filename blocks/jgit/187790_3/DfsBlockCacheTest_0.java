		DfsPackFile[] r1Packs = r1.getObjectDatabase().getPacks();
		DfsPackFile[] r2Packs = r2.getObjectDatabase().getPacks();
		assertEquals(r1Packs.length

		for (int i = 0; i < r1.getObjectDatabase().getPacks().length; ++i) {
			DfsPackFile pack1 = r1Packs[i];
			DfsPackFile pack2 = r2Packs[i];
			if (pack1.isGarbage() || pack2.isGarbage()) {
