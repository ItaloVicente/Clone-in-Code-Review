				}
				acc = acc * 85 + decoded;
			}

			for (int i = 24; i >= 0
					&& resultPtr < expectedDecodedSize; i -= 8) {
				result[resultPtr++] = (byte) ((acc >>> i) & 0xFF);
			}
		}

		return result;
	}
