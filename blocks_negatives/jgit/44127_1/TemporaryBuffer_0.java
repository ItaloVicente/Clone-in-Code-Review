		if (inCoreLimit < Block.SZ) {
			blocks = new ArrayList<Block>(1);
			blocks.add(new Block(inCoreLimit));
		} else {
			blocks = new ArrayList<Block>(initialBlocks);
			blocks.add(new Block());
		}
