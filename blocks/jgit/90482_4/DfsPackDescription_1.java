		if (as != null && as == bs && isGC(as)) {
			int cmp = Long.signum(getFileSize(PACK) - b.getFileSize(PACK));
			if (cmp != 0) {
				return cmp;
			}
		}

