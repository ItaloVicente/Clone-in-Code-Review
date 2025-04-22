
	private void handleShallowUnshallowLines() throws IOException {
		final RepositoryShallow shallow = this.local
				.getRepositoryShallowHandler();
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

