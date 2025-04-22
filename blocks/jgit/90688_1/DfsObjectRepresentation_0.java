		switch (pack.getPackDescription().getPackSource()) {
		case GC:
		case GC_REST:
		case GC_TXN:
			return true;
		default:
			return false;
		}
