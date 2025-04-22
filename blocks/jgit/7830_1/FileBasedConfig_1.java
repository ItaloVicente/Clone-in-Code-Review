				final String decoded;
				if (in.length >= 3 && in[0] == (byte) 0xEF
						&& in[1] == (byte) 0xBB && in[2] == (byte) 0xBF) {
					decoded = RawParseUtils.decode(RawParseUtils.UTF8_CHARSET
							in
					utf8Bom = true;
				} else {
					decoded = RawParseUtils.decode(in);
				}
				fromText(decoded);
