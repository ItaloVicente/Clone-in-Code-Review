				if (isWhitespace(ac) && isWhitespace(bc)) {
					as = trimLeadingWhitespace(a.content
					bs = trimLeadingWhitespace(b.content
				} else {
					if (ac != bc) {
						return false;
					}
