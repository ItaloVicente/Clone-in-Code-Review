		initBlock(block
		int ubcDvMask = detectCollision ? UbcCheck.check(w) : 0;
		compress();

		while (ubcDvMask != 0) {
			int b = numberOfTrailingZeros(lowestOneBit(ubcDvMask));
			UbcCheck.DvInfo dv = UbcCheck.DV[b];
			for (int i = 0; i < 80; i++) {
				w2[i] = w[i] ^ dv.dm[i];
			}
			recompress(dv.testt);
			if (eq(hTmp
				foundCollision = true;
				if (safeHash) {
					compress();
					compress();
				}
				break;
			}
			ubcDvMask &= ~(1 << b);
		}
	}
