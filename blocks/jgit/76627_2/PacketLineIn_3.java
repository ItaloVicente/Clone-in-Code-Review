	void discardUntilEnd() throws IOException {
		for (;;) {
			int n = readLength();
			if (n == 0) {
				break;
			}
			IO.skipFully(in
		}
	}

