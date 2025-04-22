			case 1 :
				allBits = 0;
				for (int j = 0; j < 3; j++) {
					allBits = (allBits << 6) | decodeDigit(data[dataIndex++]);
				}
				allBits = allBits << 6;
				allBits = allBits >>> 8;
				for (int j = resultIndex + 1; j >= resultIndex; j--) {
					allBits = allBits >>> 8;
				}
				break;
			case 2 :
				allBits = 0;
				for (int j = 0; j < 2; j++) {
					allBits = (allBits << 6) | decodeDigit(data[dataIndex++]);
				}
				allBits = allBits << 6;
				allBits = allBits << 6;
				allBits = allBits >>> 8;
				allBits = allBits >>> 8;
