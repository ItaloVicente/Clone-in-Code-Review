		if (inCoreLimit < Block.SZ) {
			blocks = new ArrayList<Block>(1);
			blocks.add(new Block(inCoreLimit));
		} else {
			blocks = new ArrayList<Block>(inCoreLimit / Block.SZ);
			blocks.add(new Block());
		}
