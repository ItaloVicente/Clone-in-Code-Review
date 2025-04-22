				if (ac != bc)
					return false;

				if (isWhitespace(ac))
					as = trimLeadingWhitespace(a.content, as, ae);
				else
