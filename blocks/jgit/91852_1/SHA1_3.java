		if (detectCollision) {
			for (UbcCheck.DvInfo dv : UbcCheck.DV) {
				if (((1 << dv.maskb) & ubcDvMask) != 0) {
					for (int i = 0; i < 80; i++) {
						w2[i] = w[i] ^ dv.dm[i];
					}
					recompress(dv.testt
					if (eq(hTmp
						foundCollision = true;
						if (safeHash) {
							compress();
							compress();
						}
						break;
					}
				}
			}
		}
	}

	private void initBlock(byte[] block
