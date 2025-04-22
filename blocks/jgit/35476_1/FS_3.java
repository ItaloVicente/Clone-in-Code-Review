
	static class StreamGobbler implements Runnable {
		private final InputStream stream;

		private final PrintStream output;

		public StreamGobbler(InputStream stream
			this.stream = stream;
			this.output = output;
		}

		public void run() {
			try {
				final BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
				String line = null;
				while ((line = reader.readLine()) != null) {
					output.println(line);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
