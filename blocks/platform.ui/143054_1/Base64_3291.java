		case 1:
			allBits = 0;
			for (int j = 0; j < 3; j++) {
				allBits = (allBits << 6) | decodeDigit(data[dataIndex++]);
			}
			allBits = allBits << 6;
			allBits = allBits >>> 8;
			for (int j = resultIndex + 1; j >= resultIndex; j--) {
				result[j] = (byte) (allBits & 0xff); // Bottom 8
