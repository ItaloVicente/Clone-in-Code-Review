		SimilarityIndex src = new SimilarityIndex() {
			@Override
			void hash(byte[] raw, int ptr, final int end)
					throws TableFullException {
				while (ptr < end) {
					int hash = raw[ptr] & 0xff;
					int start = ptr;
					do {
						int c = raw[ptr++] & 0xff;
						if (c == '\n')
							break;
					} while (ptr < end && ptr - start < 64);
					add(hash, ptr - start);
				}
			}
		};
