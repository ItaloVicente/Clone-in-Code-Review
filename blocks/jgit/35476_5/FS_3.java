
	private static class StreamGobbler implements Callable<Void> {
		private final BufferedReader reader;

		private final BufferedWriter writer;

		public StreamGobbler(InputStream stream
			this.reader = new BufferedReader(new InputStreamReader(stream));
			if (output == null)
				this.writer = new BufferedWriter(new OutputStreamWriter(
						new ByteArrayOutputStream()));
			else
				this.writer = new BufferedWriter(new OutputStreamWriter(output));
		}

		public Void call() throws IOException {
			boolean writeFailure = false;

			String line = null;
			while ((line = reader.readLine()) != null) {
				if (!writeFailure) {
					try {
						writer.write(line);
						writer.newLine();
						writer.flush();
					} catch (IOException e) {
						writeFailure = true;
					}
				}
			}
			return null;
		}
	}
