					if (n.startsWith("tagger ")) {
						tagger = RawParseUtils.parsePersonIdent(n.substring("tagger ".length()));
						if (tagger == null) {
							throw new CorruptObjectException(tagId
						}
					}
