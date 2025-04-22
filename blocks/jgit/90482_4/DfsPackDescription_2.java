	static boolean isGC(PackSource s) {
		switch (s) {
		case GC:
		case GC_REST:
		case GC_TXN:
			return true;
		default:
			return false;
		}
	}

