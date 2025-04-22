				|| (fileSubSecond == 0)) {
		} else if (cacheSubSecond != fileSubSecond) {
			if (cacheSubSecond % 1_000_000 == 0) {
				fileSubSecond -= (fileSubSecond % 1_000_000);
				if (cacheSubSecond != fileSubSecond) {
					return MetadataDiff.DIFFER_BY_TIMESTAMP;
				}
			} else {
