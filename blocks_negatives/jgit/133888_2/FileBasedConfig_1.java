					final String decoded;
					if (isUtf8(in)) {
						decoded = RawParseUtils.decode(UTF_8,
								in, 3, in.length);
						utf8Bom = true;
					} else {
						decoded = RawParseUtils.decode(in);
					}
					fromText(decoded);
