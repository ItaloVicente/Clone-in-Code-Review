	@Override
	public int getFormat() {
		if (ldr instanceof DeltaPackedObjectLoader)
			return PACK_DELTA;
		if (ldr instanceof WholePackedObjectLoader)
			return PACK_WHOLE;
		return FORMAT_OTHER;
