		protected ReadableChannel openPackIndex(DfsPackDescription desc)
				throws FileNotFoundException {
			MemPack memPack = (MemPack) desc;
			if (memPack.packIndex == null)
				throw new FileNotFoundException(desc.getIndexName());
			return new ByteArrayReadableChannel(memPack.packIndex);
		}

		@Override
		protected DfsOutputStream writePackFile(DfsPackDescription desc) {
			final MemPack memPack = (MemPack) desc;
			return new Out() {
				@Override
				public void flush() {
					memPack.packFile = getData();
				}
			};
		}

		@Override
		protected DfsOutputStream writePackIndex(DfsPackDescription desc) {
