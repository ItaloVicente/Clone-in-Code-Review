			for (;;) {
				if (ptr == sz)
					throw new CorruptObjectException("truncated in name");
				final byte c = raw[ptr++];
				if (c == 0)
					break;
				if (c == '/')
					throw new CorruptObjectException("name contains '/'");
				if (windows && isInvalidOnWindows(c)) {
					if (c > 31)
						throw new CorruptObjectException(String.format(
								"name contains '%c'", c));
					throw new CorruptObjectException(String.format(
							"name contains byte 0x%x", c & 0xff));
				}
			}
			checkPathSegment(raw, thisNameB, ptr - 1);
			if (duplicateName(raw, thisNameB, ptr - 1))
