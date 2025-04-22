
	@Override
	public boolean wasDeltaAttempted() {
		if (pack != null) {
			PackSource source = pack.getPackDescription().getPackSource();
			return source == GC || source == UNREACHABLE_GARBAGE;
		}
		return false;
	}
