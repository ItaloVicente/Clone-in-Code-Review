		}
	}

	public static byte[] encode85(byte[] buffer
		int srcPtr = start;
		int tgtPtr = 0;

		byte[] result = new byte[(int) Math.ceil(length / 4D) * 5];
		while (length > 0) {
			long acc = 0;
			for (int bitsToShift = 24; bitsToShift >= 0; bitsToShift -= 8) {
				byte ch = buffer[srcPtr++];
				acc |= ((ch & 0xff) << bitsToShift);
				if (--length == 0)
					break;
			}

			acc = acc & 0xFFFFFFFFL;
			for (int i = 4; i >= 0; i--) {
				int val = (int) (acc % 85);
				acc /= 85;
				result[tgtPtr + i] = (byte) enc85[val];
			}
			tgtPtr += 5;
		}

		return result;
	}

