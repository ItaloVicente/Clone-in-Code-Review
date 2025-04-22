		@Override
		public InputStream openInputStreamWithAutoDestroy() throws IOException {
			if (onDiskFile == null) {
				return super.openInputStreamWithAutoDestroy();
			}
			return new FileInputStream(onDiskFile) {
				@Override
				public void close() throws IOException {
					super.close();
					destroy();
				}
			};
		}

