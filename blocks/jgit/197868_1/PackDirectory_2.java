	private  Boolean isBitmapModified(Pack oldPack
		if (!oldPack.getBitMapFileSnapshot().isPresent() && bitmapFile == null) {
			return false;
		}
		return oldPack.getBitMapFileSnapshot()
				.map(f -> bitmapFile == null || f.isModified(bitmapFile)).orElse(true);
	}

