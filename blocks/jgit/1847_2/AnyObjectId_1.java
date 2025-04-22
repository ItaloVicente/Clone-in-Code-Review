	public final int getByte(int index) {
		int w;
		switch (index >> 2) {
		case 0:
			w = w1;
			break;
		case 1:
			w = w2;
			break;
		case 2:
			w = w3;
			break;
		case 3:
			w = w4;
			break;
		case 4:
			w = w5;
			break;
		default:
			throw new ArrayIndexOutOfBoundsException(index);
		}

		return (w >>> (8 * (3 - (index & 3)))) & 0xff;
	}

