		out = new AtomicObjectOutputStream(path, id);
		return Channels.newChannel(out);
	}

	/**
	 * Abort the output stream
	 */
	void abortWrite() {
		if (out != null) {
			out.abort();
		}
