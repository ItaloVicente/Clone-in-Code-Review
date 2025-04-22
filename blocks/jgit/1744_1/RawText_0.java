	public String getString(int i) {
		return getString(i
	}

	public String getString(int begin
		if (begin == end)
			return "";

		int s = getStart(begin);
		int e = getEnd(end - 1);
		if (dropLF && content[e - 1] == '\n')
			e--;
		return decode(s
	}

	protected String decode(int start
		return RawParseUtils.decode(content
	}

	private int getStart(final int i) {
		return lines.get(i + 1);
	}

	private int getEnd(final int i) {
		return lines.get(i + 2);
	}

