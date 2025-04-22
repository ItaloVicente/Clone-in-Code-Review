				byte c1 = bytes[i + 1]
				int val;
				try {
					val = (RawParseUtils.parseHexInt4(c1) << 4)
							| RawParseUtils.parseHexInt4(c2);
				} catch (ArrayIndexOutOfBoundsException e) {
					throw new URISyntaxException(s
				}
