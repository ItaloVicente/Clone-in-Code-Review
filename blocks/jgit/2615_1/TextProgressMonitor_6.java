	private void send(StringBuilder s) {
		if (write) {
			try {
				out.write(s.toString());
				out.flush();
			} catch (IOException err) {
				write = false;
			}
