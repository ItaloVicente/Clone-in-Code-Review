					parseComments(buf
			} else {
				tokenBegin = nextParsableToken(buf
				if (tokenBegin == -1) {
					if (includeComments)
						r.add(new RebaseTodoLine(RawParseUtils.decode(buf
								lineStart
					continue;
