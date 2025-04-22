		case 1:
			allBits = data[dataIndex++]; // actual byte
			allBits = allBits << 8; // 8 bits of zeroes
			allBits = allBits << 8; // 8 bits of zeroes
			for (int j = resultIndex + 3; j >= resultIndex; j--) {
				result[j] = (byte) digits[(allBits & 0x3f)]; // Bottom
				allBits = allBits >>> 6;
			}
			result[result.length - 1] = (byte) '=';
			result[result.length - 2] = (byte) '=';
			break;
		case 2:
			allBits = data[dataIndex++]; // actual byte
			allBits = (allBits << 8) | (data[dataIndex++] & 0xff); // actual
			allBits = allBits << 8; // 8 bits of zeroes
			for (int j = resultIndex + 3; j >= resultIndex; j--) {
				result[j] = (byte) digits[(allBits & 0x3f)]; // Bottom
				allBits = allBits >>> 6;
			}
			result[result.length - 1] = (byte) '=';
			break;
