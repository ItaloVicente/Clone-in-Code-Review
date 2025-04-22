		if (startOff == off)
			return -1;

		int read = off - startOff;
		dstBytes += read;
		return read;
