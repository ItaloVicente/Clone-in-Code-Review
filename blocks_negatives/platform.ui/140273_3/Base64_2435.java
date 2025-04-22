			case 1 :
				for (int j = resultIndex + 3; j >= resultIndex; j--) {
					allBits = allBits >>> 6;
				}
				result[result.length - 1] = (byte) '=';
				result[result.length - 2] = (byte) '=';
				break;
			case 2 :
				for (int j = resultIndex + 3; j >= resultIndex; j--) {
					allBits = allBits >>> 6;
				}
				result[result.length - 1] = (byte) '=';
				break;
