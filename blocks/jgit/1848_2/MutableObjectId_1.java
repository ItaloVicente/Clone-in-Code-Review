	public void setByte(int index
		switch (index >> 2) {
		case 0:
			w1 = set(w1
			break;
		case 1:
			w2 = set(w2
			break;
		case 2:
			w3 = set(w3
			break;
		case 3:
			w4 = set(w4
			break;
		case 4:
			w5 = set(w5
			break;
		default:
			throw new ArrayIndexOutOfBoundsException(index);
		}
	}

	private static int set(int w
		value &= 0xff;

		switch (index) {
		case 0:
			return (w & 0x00ffffff) | (value << 24);
		case 1:
			return (w & 0xff00ffff) | (value << 16);
		case 2:
			return (w & 0xffff00ff) | (value << 8);
		case 3:
			return (w & 0xffffff00) | value;
		default:
			throw new ArrayIndexOutOfBoundsException();
		}
	}

