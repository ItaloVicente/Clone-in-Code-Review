				|| (fileSubSecond == 0)) {
		} else if (cacheSubSecond != fileSubSecond) {
			int cacheSubMillis = cacheSubSecond % 1_000_000;
			int fileSubMillis = fileSubSecond % 1_000_000;
			if (cacheSubMillis == 0) {
				fileSubSecond -= fileSubMillis;
				if (cacheSubSecond != fileSubSecond) {
					return MetadataDiff.DIFFER_BY_TIMESTAMP;
				}
			} else if (fileSubMillis == 0) {
				cacheSubSecond -= cacheSubMillis;
				if (cacheSubSecond != fileSubSecond) {
					return MetadataDiff.DIFFER_BY_TIMESTAMP;
				}
			} else {
				int cacheSubMicros = cacheSubSecond % 1000;
				int fileSubMicros = fileSubSecond % 1000;
				if (cacheSubMicros == 0 || fileSubMicros == 0) {
					cacheSubSecond -= cacheSubMicros;
					fileSubSecond -= fileSubMicros;
					if (cacheSubSecond != fileSubSecond) {
						return MetadataDiff.DIFFER_BY_TIMESTAMP;
					}
				} else {
					return MetadataDiff.DIFFER_BY_TIMESTAMP;
				}
