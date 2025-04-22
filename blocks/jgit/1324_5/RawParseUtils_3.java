	private static int lastIndexOfTrim(byte[] raw
		while (pos >= 0 && raw[pos] == ' ')
			pos--;

		while (pos >= 0 && raw[pos] != ch)
			pos--;

		return pos;
	}

