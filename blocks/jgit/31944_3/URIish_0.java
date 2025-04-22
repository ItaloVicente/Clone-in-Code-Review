				byte c1 = bytes[i + 1];
				byte c2 = bytes[i + 2];
				int val;
				try {
					val = parseHexByte(c1
				} catch (ArrayIndexOutOfBoundsException e) {
					throw new URISyntaxException(s
				}
