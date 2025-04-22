		idGrowAt = growAt(idHashBits);

		try {
			idHash = new long[1 << idHashBits];
		} catch (OutOfMemoryError noMemory) {
			throw TABLE_FULL_OUT_OF_MEMORY;
		}

