	void discardUntilEnd() {
		try {
			for (;;) {
				int n = readLength();
				if (n == 0) {
					break;
				}
				IO.skipFully(in
			}
		} catch (IOException e) {
		}
	}

