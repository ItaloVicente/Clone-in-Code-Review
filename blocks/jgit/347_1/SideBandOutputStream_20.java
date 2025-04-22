	SideBandOutputStream(final int chan
		if (chan <= 0 || chan > 255)
			throw new IllegalArgumentException("channel " + chan
					+ " must be in range [0
		if (sz <= HDR_SIZE)
			throw new IllegalArgumentException("packet size " + sz
					+ " must be >= " + HDR_SIZE);
		else if (MAX_BUF < sz)
			throw new IllegalArgumentException("packet size " + sz
					+ " must be <= " + MAX_BUF);

		out = os;
		buffer = new byte[sz];
		buffer[4] = (byte) chan;
		cnt = HDR_SIZE;
