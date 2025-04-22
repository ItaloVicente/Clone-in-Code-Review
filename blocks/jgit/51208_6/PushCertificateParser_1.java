			String next = reader.read();
			if (next.startsWith(PUSHEE)) {
				pushee = parseHeader(next
				receivedNonce = parseHeader(reader
			} else {
				receivedNonce = parseHeader(next
			}
