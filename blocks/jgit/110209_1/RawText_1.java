		IntList map;
		try {
			map = RawParseUtils.lineMap(content
		} catch (BinaryBlobException e) {
			map = new IntList(3);
			map.add(Integer.MIN_VALUE);
			map.add(0);
			map.add(content.length);
		}
		lines = map;
	}

	private RawText(final byte[] data
		content = data;
		lines = lineMap;
