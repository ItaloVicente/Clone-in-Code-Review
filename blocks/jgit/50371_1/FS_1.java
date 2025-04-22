		public StreamGobbler(InputStream stream
			this.binary = binary;
			if (!binary) {
				this.reader = new BufferedReader(new InputStreamReader(stream));
				if (output != null)
					this.writer = new BufferedWriter(new OutputStreamWriter(
							output));
			} else {
				this.in = stream;
				this.out = output;
			}
