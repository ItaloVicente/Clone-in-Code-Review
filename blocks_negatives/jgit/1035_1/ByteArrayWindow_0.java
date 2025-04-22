	protected int inflate(final int pos, final byte[] b, int o,
			final Inflater inf) throws DataFormatException {
		while (!inf.finished()) {
			if (inf.needsInput()) {
				inf.setInput(array, pos, array.length - pos);
				break;
			}
			o += inf.inflate(b, o, b.length - o);
		}
		while (!inf.finished() && !inf.needsInput())
			o += inf.inflate(b, o, b.length - o);
		return o;
