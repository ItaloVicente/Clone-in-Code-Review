		try {
			wantReadAhead = true;
			((DfsCachedPack) pack).copyAsIs(out, validate, this);
		} finally {
			cancelReadAhead();
		}
