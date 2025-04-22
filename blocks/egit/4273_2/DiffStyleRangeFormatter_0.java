
		protected void flushLine() throws IOException {
			if (lineBuffer.length() > 0) {
				write(lineBuffer.toString());
				lineBuffer.setLength(0);
			}
		}
