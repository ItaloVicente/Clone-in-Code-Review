					memPack.packFile = getData();
				}
			};
		}

		@Override
		protected DfsOutputStream writePackIndex(DfsPackDescription desc) {
			final MemPack memPack = (MemPack) desc;
			return new Out() {
				@Override
				public void flush() {
					memPack.packIndex = getData();
