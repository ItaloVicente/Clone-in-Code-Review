					final String decoded;
					if (isUtf8(in)) {
						decoded = RawParseUtils.decode(
								RawParseUtils.UTF8_CHARSET
						utf8Bom = true;
					} else {
						decoded = RawParseUtils.decode(in);
					}
					fromText(decoded);
					snapshot = newSnapshot;
					hash = newHash;
