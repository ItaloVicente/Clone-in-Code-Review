		IntList map;
		try {
			map = RawParseUtils.lineMap(content, 0, content.length);
		} catch (BinaryBlobException e) {
			map = new IntList(3);
			map.add(Integer.MIN_VALUE);
			map.add(0);
			map.add(content.length);
		}
		lines = map;
	}

	/**
	 * Construct a new RawText if the line map is already known.
	 *
	 * @param data
	 *   the blob data.
	 * @param lineMap
	 *   Indices of line starts, with indexed by base-1 linenumber.
	 */
	private RawText(final byte[] data, final IntList lineMap) {
		content = data;
		lines = lineMap;
