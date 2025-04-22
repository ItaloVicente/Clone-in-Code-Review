	private String format(int value) {
		if (value > GB && (value / GB) * GB == value)
		if (value > MB && (value / MB) * MB == value)
		if (value > KB && (value / KB) * KB == value)
		return String.valueOf(value);
	}

