	private void send(StringBuilder s) {
		if (write) {
			try {
				out.write(Constants.encode(s.toString()));
				out.flush();
			} catch (IOException err) {
				write = false;
			}
