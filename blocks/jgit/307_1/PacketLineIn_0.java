		byte[] raw;
		if (len < lineBuffer.length)
			raw = lineBuffer;
		else
			raw = new byte[len];

