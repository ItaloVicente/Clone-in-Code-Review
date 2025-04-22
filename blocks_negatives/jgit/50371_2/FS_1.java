		public StreamGobbler(InputStream stream, OutputStream output) {
			this.reader = new BufferedReader(new InputStreamReader(stream));
			if (output == null)
				this.writer = null;
			else
				this.writer = new BufferedWriter(new OutputStreamWriter(output));
