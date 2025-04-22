			} else {
				final String decoded;
				if (isUtf8(in) && in != null) {
					decoded = RawParseUtils.decode(UTF_8
					utf8Bom = true;
				} else {
					decoded = RawParseUtils.decode(in);
