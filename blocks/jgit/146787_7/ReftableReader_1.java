		if (blockType == LOG_BLOCK_TYPE) {
			BlockReader block = readBlock(startPos

			for (;;) {
				if (block == null || block.type() != LOG_BLOCK_TYPE) {
					return null;
				}

				int result = block.seekKey(key);
				if (result <= 0) {
					return block;
				}

				long pos = block.endPosition();
				if (pos >= endPos) {
					return null;
				}
				block = readBlock(pos
			}
		}
