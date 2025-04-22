
	public static class PacketLineInIterator implements Iterable<String> {
		private PacketLineIn in;

		private String current;

		PacketLineInIterator(PacketLineIn in) throws IOException {
			this.in = in;
			current = in.readString();
		}

		@Override
		public Iterator<String> iterator() {
			return new Iterator<String>() {
				@Override
				public boolean hasNext() {
					return !PacketLineIn.isEnd(current);
				}

				@Override
				public String next() {
					String next = current;
					try {
						current = in.readString();
					} catch (IOException e) {
						throw new UncheckedIOException(e);
					}
					return next;
				}
			};
		}

	}
