		} else {
			table = new int[tableSize(blockCnt)];
			tableMask = table.length - 1;

			hash = new int[1 + blockCnt];
			ptrs = new long[hash.length];
			next = new int[hash.length];
		}
