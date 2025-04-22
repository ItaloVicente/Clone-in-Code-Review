		}
		if (bufLen < blockLen) {
			if (blockType != INDEX_BLOCK_TYPE) {
				throw invalidBlock();
			}
			truncated = true;
