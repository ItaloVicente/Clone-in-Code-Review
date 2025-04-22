			if (thisNameB + 1 == ptr)
				throw new CorruptObjectException("zero length name");
			if (raw[thisNameB] == '.') {
				final int nameLen = (ptr - 1) - thisNameB;
				if (nameLen == 1)
					throw new CorruptObjectException("invalid name '.'");
				if (nameLen == 2 && raw[thisNameB + 1] == '.')
					throw new CorruptObjectException("invalid name '..'");
			}
