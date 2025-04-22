				allBits = allBits >>> 8;
			}
			break;
		case 2:
			allBits = 0;
			for (int j = 0; j < 2; j++) {
				allBits = (allBits << 6) | decodeDigit(data[dataIndex++]);
			}
			allBits = allBits << 6;
			allBits = allBits << 6;
			allBits = allBits >>> 8;
			allBits = allBits >>> 8;
			result[resultIndex] = (byte) (allBits & 0xff); // Bottom
			break;
