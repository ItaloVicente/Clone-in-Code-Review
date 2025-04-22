		for (int ptr = 0; ptr < length; ptr++) {
			byte curr = raw[ptr];
			if (isBinary(curr
				return false;
			}
			if (curr == '\n' && last == '\r') {
