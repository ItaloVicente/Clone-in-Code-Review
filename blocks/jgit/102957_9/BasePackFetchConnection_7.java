
	private void handleShallowUnshallowLines() throws IOException {
		final FileBasedShallow shallow = new FileBasedShallow(this.local);
		shallow.lock();
		shallow.read();
		for (String line;;) {
			line = pckIn.readString();
			if (!shallow.parseShallowUnshallowLine(line)) {
				break;
			}
		}
		shallow.unlock(true);
	}

