		class HttpInputStream extends InputStream {
			private final UnionInputStream src;

			HttpInputStream(UnionInputStream httpIn) {
				this.src = httpIn;
			}

			private InputStream self() throws IOException {
				if (src.isEmpty()) {
					execute();
				}
				return src;
			}

