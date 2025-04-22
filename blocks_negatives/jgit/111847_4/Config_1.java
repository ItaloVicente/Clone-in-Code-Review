			byte[] bytes = IO.readFully(path);
			String decoded;
			if (isUtf8(bytes)) {
				decoded = RawParseUtils.decode(RawParseUtils.UTF8_CHARSET,
						bytes, 3, bytes.length);
			} else {
				decoded = RawParseUtils.decode(bytes);
			}
			newEntries.addAll(fromTextRecurse(decoded, depth + 1));
